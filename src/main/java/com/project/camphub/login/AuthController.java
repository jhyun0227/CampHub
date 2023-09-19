package com.project.camphub.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@Controller
public class AuthController {

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
