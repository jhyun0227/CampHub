package com.project.camphub.login.oauth;

import com.project.camphub.login.SecurityProperties;
import com.project.camphub.login.jwt.JwtTokenProvider;
import com.project.camphub.login.jwt.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //로그인 성공 후 SecurityContext에 저장된 정보를 가져오기
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        log.info("OAuth2SuccessHandler.oAuth2User = {}", oAuth2User.toString());

        //토큰 발급
        String mbEmail = (String) oAuth2User.getAttribute("email");
        String authority = oAuth2User.getAuthorities().iterator().next().getAuthority().substring(5);
        TokenDto tokenDto = jwtTokenProvider.generateToken(mbEmail, authority);
        log.info("OAuth2SuccessHandler.tokenDto = {}", tokenDto);

        //Cookie에 토큰 적용
        jwtTokenProvider.setCookieInResponse(response, tokenDto);

        Cookie[] cookies = request.getCookies();
        String redirect = "/main";

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (SecurityProperties.REDIRECT.equals(cookie.getName())) {
                    redirect = cookie.getValue();
                }
            }
        }

        response.setContentType(redirect);
    }

}