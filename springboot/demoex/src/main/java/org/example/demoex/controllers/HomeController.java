package org.example.demoex.controllers;

import org.example.demoex.dto.News;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *  SSR (Server Side Rendering) : Springboot <-> CSR (Client Side Rendering) : react
 */

@Controller
public class HomeController {
    // 1. URL 직접 부여
    @GetMapping("/")
    public String home() {
        // @Controller 이고 타임리프(thymeleaf) 설치했다면
        // reutrn "index"; => index.html을 찾아서 렌더링하여 응답하라는 뜻 (SSR)
        // index.html 생성 -> src/main/resources/templates/*.html
        // 롬복 확인
        // new News();
        // 빌더 패턴으로 객체를 생성
        News news = News.builder()
                .title("스프츠 뉴스")
                .content("토트텀 리버풀 경기")
                .author("기자")
                .build();
        // 로그
        System.out.println(news.toString()); // 전체 데이터 출력
        // 개별 데이터 출력 getter(롬복에 의해 자동 생성)
        System.out.println(news.getAuthor());
        System.out.println(news.getTitle());
        System.out.println(news.getContent());
        news.setContent("3:6");
        System.out.println(news.getContent());

        return "index";
    }

    // 2. 직접 복잡한 URL 구성
    // 속한 prefix 무시
    // ex) ~/test/news/sports
    @GetMapping("/test/news/sports")
    public String sports() {
        return "sports";
    }

    // 3. Get 방식, URL: ~/news, 전달 데이터: id, servicetype
    // id: 뉴스 번호, servicetype: 매체(지연, 동영상, 쇼츠, 블로그...)
    // 요청 URL: http://localhost:8080/news?id=2423424223&servicetype=video
    @GetMapping("/news")
    // html이 아닌 텍스트로 응답 @ResponseBody
    @ResponseBody
    public String news(@RequestParam(value="id") String id,
                       @RequestParam(value="servicetype") String servicetype) {
        // 3-1 클라이언트의 요청에서 데이터를 추출 (현재 get방식)
        return "news " + id + " " + servicetype;
    }

    // 4. Get 방식 + URL path(URL 어디든 존재 가능, {변수명})
    // URL/데이터?파라미터(키=값...)
    // ? => /10?
    // 요청 URL: http://localhost:8080/news/10?id=2423424223&servicetype=video
    // nid : 뉴스 카테고리 번호로 설정
    @GetMapping("/news/{nid}")
    @ResponseBody
    public String news2(@PathVariable("nid") String nid,
                        @RequestParam(value="id") String id,
                        @RequestParam(value="servicetype") String servicetype) {
        return nid + " <- news " + id + " " + servicetype;
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "board/post_list";
    }

    /**
     * 타임리프, 템플릿 분할 및 조립 연습용 페이지
     *  - html을 분할하여 공통부분 조립하는 단위 테스트
     * @return
     */
    @GetMapping("/test/layout")
    public String layout() {
        return "ui/index";
    }
}




