package com.project.camphub.common.config;

import com.project.camphub.login.oauth.OAuth2UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity //Security 활성화 의미, Security 필터가 Spring filter체인에 등록
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2UserServiceImpl oAuth2UserServiceImpl;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()

                /**
                 * 인증 및 인가 설정
                 */
                .authorizeRequests()
                .antMatchers("/", "/login", "/external/**", "/css/**", "/images/**", "/js/**").permitAll()
//                  .antMatchers("/admin/**").hasRole("ROLE_ADMIN")
                .anyRequest().authenticated()

                /**
                 * Jwt 사용을 위한 세션 기능 동작X
                 */
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                /**
                 * 소셜 로그인 설정
                 */
                .and()
                .oauth2Login()
                    .loginPage("/login") //Security 기본 로그인 기능 사용하지 않음
                        .userInfoEndpoint() // 클라이언트가 AccessToken을 이용해서 가져온 정보를 사용하는데 사용되는 서비스를 지정한다.
                            .userService(oAuth2UserServiceImpl);

        return httpSecurity.build();
    }
}
