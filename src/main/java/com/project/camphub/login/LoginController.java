package com.project.camphub.login;

import com.project.camphub.login.resolver.Login;
import com.project.camphub.member.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@Login Member member) {

        log.info("/login 진입");

        if (member != null) {
            log.info("이미 로그인한 회원");
            return "redirect:/main";
        }

        return "login/loginForm";
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "auth/accessDenied";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @GetMapping("/usernameNotFound")
    public String usernameNotFound() {
        return "auth/usernameNotFound";
    }
}
