package com.project.camphub.login.oauth;

import com.project.camphub.login.jwt.JwtTokenProvider;
import com.project.camphub.login.jwt.TokenDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    private final Integer accessTokenValiditySeconds;
    private final Integer refreshTokenValiditySeconds;

    @Autowired
    public OAuth2SuccessHandler(
            JwtTokenProvider jwtTokenProvider,
            @Value("${jwt.access-token-validity-in-seconds}") Integer accessTokenValiditySeconds,
            @Value("${jwt.refresh-token-validity-in-seconds}") Integer refreshTokenValiditySeconds) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //로그인 성공 후 SecurityContext에 저장된 정보를 가져오기
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        log.info("oAuth2User = {}", oAuth2User.toString());

        //토큰 발급
        TokenDto tokenDto = jwtTokenProvider.generateToken(oAuth2User);
        log.info("tokenDto = {}", tokenDto);

        //header에 token 적용
        this.responseToken(response, tokenDto);
    }

    /**
     * response 헤더에 토큰을 적용
     * 같은 도메인에서 SSR과 CSR 방식을 둘 다 사용하기 위해 편의성을 위해 토큰을 Header에 저장하지 않고, Cookie에 저장
     * 노션 정리5
     */
    private void responseToken(HttpServletResponse response, TokenDto tokenDto) {
        response.setContentType("text/html;charset=UTF-8");

//        response.addHeader("Authorization", tokenDto.getAccessToken());
//        response.addHeader("Refresh", tokenDto.getRefreshToken());

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
        String accessTokenCookie = "ACCESS=" + tokenDto.getAccessToken() + "; Max-Age=" + refreshTokenValiditySeconds + "; HttpOnly; Path=/; SameSite=Strict";
        response.addHeader("Set-Cookie", accessTokenCookie);

        String refreshTokenCookie = "REFRESH=" + tokenDto.getRefreshToken() + "; Max-Age=" + refreshTokenValiditySeconds + "; HttpOnly; Path=/; SameSite=Strict";
        response.addHeader("Set-Cookie", refreshTokenCookie);
    }
}