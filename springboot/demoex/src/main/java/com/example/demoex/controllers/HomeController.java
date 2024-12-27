package com.example.demoex.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *  SSR (Server Side Rendering) : Springboot <-> CSR (Client Side Rendering) : react
 */

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        // @Controller 이고 타임리프(thymeleaf) 설치했다면
        // reutrn "index"; => index.html을 찾아서 렌더링하여 응답하라는 뜻 (SSR)
        // index.html 생성 -> src/main/resources/templates/*.html
        return "index";
    }
}
