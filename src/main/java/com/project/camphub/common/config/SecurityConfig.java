package com.project.camphub.common.config;

import com.project.camphub.login.auth.CustomAuthenticationFailureHandler;
import com.project.camphub.login.jwt.JwtAccessDeniedHandler;
import com.project.camphub.login.jwt.JwtAuthenticationEntryPoint;
import com.project.camphub.login.jwt.JwtAuthenticationFilter;
import com.project.camphub.login.oauth.OAuth2SuccessHandler;
import com.project.camphub.login.oauth.OAuth2UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity //Security 활성화 의미, Security 필터가 Spring filter체인에 등록
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2UserServiceImpl oAuth2UserServiceImpl;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    private final JwtAuthenticationFilter jwtAuthenticationFilter; //Token 검증 필터

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()

                //formLogin.disable()을 하면 UsernmaePasswordAuthenticationFilter가 동작을 하지 않는다.
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                /**
                 * 예외처리
                 */
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                /**
                 * 인증 및 인가 설정
                 */
                .and()
                .authorizeRequests()
//                .antMatchers("/", "/login", "/external/**", "/css/**", "/images/**", "/js/**").permitAll()
//                  .antMatchers("/admin/**").hasRole("ROLE_ADMIN")
                .antMatchers("/member/logout").authenticated()
                .anyRequest().permitAll()

                /**
                 * Jwt 사용을 위한 세션 기능 동작X
                 */
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                /**
                 * 로그아웃 설정
                 */
                .and()
                .logout()
                    .disable()

                /**
                 * 소셜 로그인 설정
                 */
                .oauth2Login()
                    .loginPage("/member/login") //Security 기본 로그인 기능 사용하지 않음
                    .successHandler(oAuth2SuccessHandler)
                    .failureHandler(customAuthenticationFailureHandler)
                    .userInfoEndpoint() // 클라이언트가 AccessToken을 이용해서 가져온 정보를 사용하는데 사용되는 서비스를 지정한다.
                        .userService(oAuth2UserServiceImpl);

        return httpSecurity.build();
    }
}
