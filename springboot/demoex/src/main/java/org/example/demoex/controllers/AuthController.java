package org.example.demoex.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *  인증 처리
 *      - URL prefix 처리를 통한 URL 구성 실습
 *          - auth
 */
@RequestMapping("/auth") // 컨트롤러 내부의 모든 라우트 함수는 앞쪽에 /auth를 붙인다
@Controller
public class AuthController {
    // /auth/login 처리, get 방식 정의(@GetMapping) 구성
    // 접속 테스트 : http://localhost:8080/auth/login
    // 1. URL 적용, 메소드(GET|POST|..) 방식과 함께
    @GetMapping("/login")
    public String login() {
        return "login"; // login.html
    }
}
