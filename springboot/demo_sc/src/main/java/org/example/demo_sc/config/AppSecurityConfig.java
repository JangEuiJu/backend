package org.example.demo_sc.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

/**
 * 스프링 시큐리티의 정책, 보안적 설정 등등 기술
 */

@RequiredArgsConstructor
@Configuration
public class AppSecurityConfig {
    // DI : DI 사용시 문제점 => 순환 참조(무한) 발생 여지 있음-> 방향성 고려, 방식 섞어서 사용
    //     생성자 방식으로 DI 구성
    private final UserDetailsService userDetailsService;

    // 모두다 @Bean 처리

    // 2. 전체 페이지 권한 여부 설정 (가장 중요, 메인)
    //    - 인증이 필요한 페이지, 필요 없는 페이지 등등 설정
    //    - 로그인 페이지(커스텀), 로그인 성공 후 포워딩 페이지 등 지정
    //    - 로그아웃 처리 등 추가 처리
    //    - csrf 공격 대응 처리
    //    - 기타 필요 처리...
    //    - 시큐리티 5.x -> 6.x 변경되면서 바뀐 메소드
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // a. 인증이 필요한 페이지와 아닌 페이지
                .authorizeRequests()
                    // 아래 페이지는 인증 필요 x (로그인, 회원가입)
                    .requestMatchers("/login", "/signup", "/user").permitAll()
                    // 나머지 모든 페이지는 인증 필요
                    .anyRequest().authenticated()
                .and()
                // b. 로그인 페이지(커스텀), 로그인 성공 후 포워딩 페이지 등 지정
                .formLogin()
                    .loginPage("/login")    // `커스텀` 로그인 페이지 이동
                    .defaultSuccessUrl("/") // 로그인 성공하면 홈페이지 포워딩
                .and()
                // c. 로그아웃 처리 등 추가 처리
                .logout()
                    // 로그아웃 요청은 인증이 있을때만 가능하다!! -> URL 지정 x, 기능만 붙이면 어떤 url도 로그아웃이 될 수 있음
                    .logoutSuccessUrl("/login")  // 인증 없이는 접근이 안되게 구성 -> 다시 로그인
                    .invalidateHttpSession(true) // 세션(서버측에 클라이언트 정보 저장관리 객체) 처리
                .and()
                // d. csrf 공격 대응 처리
                .csrf()
                    // 활성화 처리되면 => form 태그 밑에 해당 값에 대한 설정이 필요(hidden 타입)
                    .disable()   // 안함!! -> 나중에 활성화!!
                .build();
    }

    // 1. 예외처리 -> 인증에 상관없이 원래 사용하던대로 자유롭게 접근 가능
    //    - h2 console
    //    - 정적 데이터 -> resource/static/js|css|image|...
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 다음 등록된 내용은 보안 정책이 무시된다
        return webSecurity -> webSecurity.ignoring()
                .requestMatchers(toH2Console())     // ~/h2-console
                .requestMatchers("/static/**");   // 정적 데이터 모든것
    }
}
