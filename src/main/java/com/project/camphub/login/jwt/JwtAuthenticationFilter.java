package com.project.camphub.login.jwt;

import com.project.camphub.login.SecurityProperties;
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

        String accessToken = getAccessToken(request);

        //토큰이 유효한 경우 통과, 토큰이 만료한 경우에는 재발급 여부를 고려한다.
        if (StringUtils.hasText(accessToken)) {
            String checkResult = jwtTokenProvider.checkAccessToken(accessToken);

            if (SecurityProperties.VALID.equals(checkResult)) { //유효한 토큰

                Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("Valid AccessToken, Save Authentication in SecurityContextHolder");

            } else if (SecurityProperties.EXPIRED.equals(checkResult)) { //기간이 만료된 토큰, Refresh 토큰의 만료여부를 확인한다.

                //refresh토큰을 사용자가 가지게 할것인가, redis에 저장할것인가..?

            } else { //유효하지 않은 토큰
                SecurityContextHolder.clearContext();
                log.info("InValid AccessToken");
            }


        }

        filterChain.doFilter(request, response);
    }

    /**
     * 쿠키로부터 토큰값을 가져오는 메서드
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
}
