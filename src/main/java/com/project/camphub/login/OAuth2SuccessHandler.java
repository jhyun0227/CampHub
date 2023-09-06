package com.project.camphub.login;

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
     * 이렇게 해도 되나..?
     * 노션 정리5
     */
    private void responseToken(HttpServletResponse response, TokenDto tokenDto) {
        response.setContentType("text/html;charset=UTF-8");

//        response.addHeader("Authorization", tokenDto.getAccessToken());
//        response.addHeader("Refresh", tokenDto.getRefreshToken());

        /**
         * 토큰을 쿠키에 저장
         * Path : 어떤 경로에서 쿠키를 보낼 것인지, "/"일 경우 모든 요청에 쿠키를 전달
         * HttpOnly : 자바스크립트에서 쿠키값을 꺼낼수 없도록 한다.
         * SameSite : 같은 사이트에서의 요청에서만 쿠키가 전송된다.
         */
        String accessTokenCookie = "ACCESS=" + tokenDto.getAccessToken() + "; Max-Age=" + accessTokenValiditySeconds + "; HttpOnly; Path=/; SameSite=Strict";
        response.addHeader("Set-Cookie", accessTokenCookie);

        String refreshTokenCookie = "REFRESH=" + tokenDto.getRefreshToken() + "; Max-Age=" + refreshTokenValiditySeconds + "; HttpOnly; Path=/; SameSite=Strict";
        response.addHeader("Set-Cookie", refreshTokenCookie);
    }
}
