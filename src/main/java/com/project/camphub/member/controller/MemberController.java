package com.project.camphub.member.controller;

import com.project.camphub.login.resolver.Login;
import com.project.camphub.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    @GetMapping("/login")
    public String login(@Login Member member) {

        log.info("/login 진입");

        if (member != null) {
            log.info("이미 로그인한 회원");
            return "redirect:/main";
        }

        return "login/loginForm";
    }

    @DeleteMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
