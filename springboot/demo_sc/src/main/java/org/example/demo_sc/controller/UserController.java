package org.example.demo_sc.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * - 인증 없이 접근가능함 : 로그인, 회원가입
 * - 인증 후에 접근가능함 : 로그아웃 (필요시 컨트롤러 분리 가능함)
 * - 시나리오
 *  - 회원가입 -> 로그인 -> 홈페이지 자동 이동 -> 로그아웃 클릭 -> 로그인
 */

@Controller
public class UserController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }
}
