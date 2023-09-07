package com.project.camphub.login.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.sendRedirect("/login");

        //인증이 안된 경우
        //로그인 파일로 이동, 로그인 이후 원래 페이지로 돌아와야하니까 쿠키에 requesturi넣어두기
        //나중에 ajax일 경우에는 어떡하지? uri의 구별을 주는 방법을 해야할듯?
    }
}
