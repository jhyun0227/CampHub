package com.project.camphub.login.jwt;

import com.project.camphub.login.SecurityProperties;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = this.getAccessToken(request);

        //토큰이 유효한 경우 통과, 토큰이 만료한 경우에는 재발급 여부를 고려한다.
        if (StringUtils.hasText(accessToken)) {
            String checkAccessResult = jwtTokenProvider.checkAccessToken(accessToken);

            if (SecurityProperties.VALID.equals(checkAccessResult)) { //유효한 토큰

                Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("Valid AccessToken, Save Authentication in SecurityContextHolder");

            } else if (SecurityProperties.EXPIRED.equals(checkAccessResult)) { //기간이 만료된 토큰, Refresh 토큰의 만료여부를 확인한다.

                //refresh토큰을 사용자가 가지게 할것인가, redis에 저장할것인가..? 우선 사용자가 보관하는 것으로 코드 작성
                String refreshToken = this.getRefreshToken(request);

                if (StringUtils.hasText(refreshToken)) { //RefreshToken이 존재하는 경우

                    String checkRefreshResult = jwtTokenProvider.checkRefreshToken(refreshToken);

                    if (SecurityProperties.VALID.equals(checkRefreshResult)) { //RefreshToken이 유효한 경우 토큰을 재발급한다.

                        TokenDto reissueTokenDto = this.reissueToken(accessToken); //기존 AccessToken으로 토큰 재발급
                        this.setResponseReissue(response, reissueTokenDto); //응답 쿠키에 토큰 재설정

                        Authentication authentication = jwtTokenProvider.getAuthentication(reissueTokenDto.getAccessToken());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        log.info("Reissue AccessToken, Save Authentication in SecurityContextHolder");

                    } else { //RefreshToken이 유효하지않거나 기한이 만료된 경우 로그인으로 유도
                        SecurityContextHolder.clearContext();
                        log.info("Invalid RefreshToken");
                    }

                } else { //RefreshToken이 없는 경우
                    SecurityContextHolder.clearContext();
                    log.info("Doesn't Exist RefreshToken");
                }

            } else { //AccessToken이 존재하지만 유효하지 않음
                SecurityContextHolder.clearContext();
                log.info("Invalid AccessToken");
            }

        } else { //존재하지 않는 AccessToken
            SecurityContextHolder.clearContext();
            log.info("Doesn't Exist AccessToken");
        }

        filterChain.doFilter(request, response);
    }

    /**
     * 쿠키로부터 AccessToken 토큰값을 가져오는 메서드
     */
    private String getAccessToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {
            if (SecurityProperties.ACCESS.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }

    /**
     * 쿠키로부터 RefreshToken 토큰값을 가져오는 메서드
     */
    private String getRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {
            if (SecurityProperties.REFRESH.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }

    /**
     * 토큰을 재발급 받는 메서드
     */
    private TokenDto reissueToken(String accessToken) {
        Claims claims = jwtTokenProvider.getClaims(accessToken);

        String mbEmail = claims.get(SecurityProperties.MEMBER_EMAIL).toString();
        log.info("reissueToken.mbEmail = {}", mbEmail);
        String authority = claims.get(SecurityProperties.AUTHORITY_KEY).toString();
        log.info("reissueToken.authority = {}", authority);

        return jwtTokenProvider.generateToken(mbEmail, authority);
    }

    /**
     * 응답에 token을 재설정
     */
    private void setResponseReissue(HttpServletResponse response, TokenDto tokenDto) {
        response.setContentType("text/html;charset=UTF-8");

        /**
         * Path : 어떤 경로에서 쿠키를 보낼 것인지, "/"일 경우 모든 요청에 쿠키를 전달
         * HttpOnly : 자바스크립트에서 쿠키값을 꺼낼수 없도록 한다. XSS 방지
         * SameSite : 같은 사이트에서의 요청에서만 쿠키가 전송된다. CSRF 방지
         *
         * AccessToken의 쿠키 만료시간은 RefreshToken과 같게한다. (7일)
         * AccessToken의 쿠키의 만료시간을 30분으로 설정할 경우, 30분 이후의 요청에서는 AccessToken이 Request로 전달되지 않기 떄문에 재 로그인이 필요하다.
         * 하지만 AccessToken의 쿠키시간을 RefreshToken과 같게한다면, 토큰 자체의 만료시간이 지나도 브라우저에 남아있게 되고 RefreshToken이 만료되어있지 않을 경우 자동 재발급이 가능하다.
         * 그리고 RefreshToken이 만료될경우 어차피 재로그인을 해야하기 때문에,
         * AccessToken의 자체 만료기간은 30분으로 두지만 쿠키의 만료기간은 7일로 설정한다.
         */
        String accessTokenCookie = SecurityProperties.ACCESS + "=" + tokenDto.getAccessToken() + "; Max-Age=" + refreshTokenValiditySeconds + "; HttpOnly; Path=/; SameSite=Strict";
        response.addHeader("Set-Cookie", accessTokenCookie);

        String refreshTokenCookie = SecurityProperties.REFRESH + "=" + tokenDto.getRefreshToken() + "; Max-Age=" + refreshTokenValiditySeconds + "; HttpOnly; Path=/; SameSite=Strict";
        response.addHeader("Set-Cookie", refreshTokenCookie);
    }

}
