package com.project.camphub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MainController {

    @GetMapping("/")
    public String init() {
        return this.main();
    }

    @GetMapping("/main")
    public String main() {
        return "main";
    }

    @GetMapping("/login")
    public String login() {
        return "login/loginForm";
    }


    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "auth/accessDenied";
    }

}
