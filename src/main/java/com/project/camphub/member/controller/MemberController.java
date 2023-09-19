package com.project.camphub.member.controller;

import com.project.camphub.login.LoginProperties;
import com.project.camphub.login.resolver.Login;
import com.project.camphub.member.entity.Member;
import com.project.camphub.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    /**
     * 로그인 화면 이동 메서드
     */
    @GetMapping("/login")
    public String login(@Login Member member) {

        log.info("/login 진입");

        if (member != null) {
            log.info("이미 로그인한 회원");
            return "redirect:/main";
        }

        return "login/loginForm";
    }

    /**
     * 로그아웃 메서드
     */
    @GetMapping("/logout")
    public String logout(@Login Member member, HttpServletResponse response) {

        log.info("/logout 진입");

        if (member == null) {
            log.info("로그인 되지 않음");
            return "redirect:/main";
        }

        //Redis에서 RefreshToken 삭제
        memberService.logout(member);

        //쿠키 초기화
        response.setContentType("text/html;charset=UTF-8");

        String accessTokenCookie = LoginProperties.ACCESS + "=" + "" + "; Max-Age=" + 0 + "; HttpOnly; Path=/; SameSite=Strict";
        response.addHeader("Set-Cookie", accessTokenCookie);
        String refreshTokenCookie = LoginProperties.REFRESH + "=" + "" + "; Max-Age=" + 0 + "; HttpOnly; Path=/; SameSite=Strict";
        response.addHeader("Set-Cookie", refreshTokenCookie);

        return "redirect:/main";
    }
}
