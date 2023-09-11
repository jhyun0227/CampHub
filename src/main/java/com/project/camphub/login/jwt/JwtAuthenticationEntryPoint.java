package com.project.camphub.login.jwt;

import com.project.camphub.login.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        /**
         * 인증이 안된 경우, 요청을 보내기전 위치한 페이지 (Referer)의 주소를 받아 쿠키에 저장하고 로그인페이지로 랜딩한다.
         * 로그인이 성공하면 해당 쿠키를 이용해 원래 페이지로 이동한다.
         * Ajax를 이용한 Restful API를 사용하기도 하기에, Referer로 이동하도록 한다.
         *
         * Referer가 없거나, Referer와 requertUri의 host가 다를 경우엔 메인으로 랜딩되도록 한다.
         */

        /*
        임시 주석처리 추후 변경할것

        String refererString = request.getHeader("Referer");
        log.info("refererString = {}", refererString);
        String requestUrlString = request.getRequestURL().toString();
        log.info("requestUrlString = {}", requestUrlString);

        if (!StringUtils.hasText(refererString)) {

            response.sendRedirect("/main");

        } else {
            UriComponents refererComponents = UriComponentsBuilder.fromUriString(refererString).build();
            UriComponents requestUrlComponents = UriComponentsBuilder.fromUriString(requestUrlString).build();

            if (!refererComponents.getHost().equals(requestUrlComponents.getHost())) {

                response.sendRedirect("/main");

            } else {

                String path = refererComponents.getPath();
                String query = refererComponents.getQuery();

                if (StringUtils.hasText(query)) {
                    path = path + "?" + query;
                }

                log.info("path = {}", path);
                log.info("query = {}", query);

                String redirect = SecurityProperties.REDIRECT + "=" + path + "; Max-Age=" + 60 * 5 + "; HttpOnly; Path=/; SameSite=Strict";
                response.addHeader("Set-Cookie", redirect);

                response.sendRedirect("/login");
            }

        }
        */

        response.sendRedirect("/login");
    }
}
