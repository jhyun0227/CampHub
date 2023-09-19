package com.project.camphub.login.jwt;

import com.project.camphub.login.AuthProperties;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = this.getToken(request, AuthProperties.ACCESS);

        //토큰이 유효한 경우 통과, 토큰이 만료한 경우에는 재발급 여부를 고려한다.
        if (StringUtils.hasText(accessToken)) {
            String checkAccessResult = jwtTokenProvider.checkAccessToken(accessToken);

            if (AuthProperties.VALID.equals(checkAccessResult)) { //유효한 토큰

                Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("유효한 AccessToken");

            } else if (AuthProperties.EXPIRED.equals(checkAccessResult)) { //기간이 만료된 토큰, Refresh 토큰의 만료여부를 확인한다.

                this.expiredAccessToken(request, response, accessToken);

            } else { //AccessToken이 존재하지만 유효하지 않음
                SecurityContextHolder.clearContext();
                log.error("유효하지 않은 AccessToken");
            }

        } else { //존재하지 않는 AccessToken
            SecurityContextHolder.clearContext();
            log.error("존재하지 않는 AccessToken");
        }

        filterChain.doFilter(request, response);
    }

    /**
     * AccessToken 만료 메서드 리팩토링
     * 쿠키 RefreshToken을 검사한다.
     * 쿠키 RT가 유효할 경우, RedisRepository에서 한번 더 검증한다. (이 코드는 JwtTokenProvider에 구현)
     */
    private void expiredAccessToken(HttpServletRequest request, HttpServletResponse response, String accessToken) {
        //refresh토큰을 사용자가 가지게 할것인가, redis에 저장할것인가..? 우선 사용자가 보관하는 것으로 코드 작성
        String refreshToken = this.getToken(request, AuthProperties.REFRESH);

        if (StringUtils.hasText(refreshToken)) { //RefreshToken이 존재하는 경우

            String checkRefreshResult = jwtTokenProvider.checkRefreshToken(refreshToken);

            if (AuthProperties.VALID.equals(checkRefreshResult)) { //RefreshToken이 유효한 경우 토큰을 재발급한다.

                TokenDto reissueTokenDto = this.reissueToken(accessToken); //기존 AccessToken으로 토큰 재발급
                jwtTokenProvider.setCookieInResponse(response, reissueTokenDto);

                Authentication authentication = jwtTokenProvider.getAuthentication(reissueTokenDto.getAccessToken());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("AccessToken, RefreshToken 재발급 성공");

            } else { //RefreshToken이 유효하지않거나 기한이 만료된 경우 로그인으로 유도
                SecurityContextHolder.clearContext();
                log.error("유효하지 않은 RefreshToken");
            }

        } else { //RefreshToken이 없는 경우
            SecurityContextHolder.clearContext();
            log.error("존재하지 않는 RefreshToken");
        }
    }

    /**
     * 쿠키로부터 AccessToken, RefreshToken을 가져오는 메서드
     * tokenType에 따라 다르 반환 결과가 다르다.
     */
    private String getToken(HttpServletRequest request, String tokenType) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (tokenType.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    /**
     * 토큰을 재발급 받는 메서드
     */
    private TokenDto reissueToken(String accessToken) {
        Claims claims = jwtTokenProvider.getClaims(accessToken);

        String mbEmail = claims.get(AuthProperties.MEMBER_EMAIL).toString();
        log.info("reissueToken.mbEmail = {}", mbEmail);
        String authority = claims.get(AuthProperties.AUTHORITY_KEY).toString();
        log.info("reissueToken.authority = {}", authority);

        return jwtTokenProvider.generateToken(mbEmail, authority);
    }
}
