package com.project.camphub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@Controller
public class MainController {

    @GetMapping("/")
    public String init() {
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String main() {
        return "main";
    }

    @GetMapping("/login")
    public String login() {
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
