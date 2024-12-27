package org.example.demoex;

import org.example.demoex.dto.News;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *  단위 테스트 용도
 *      - 개발한 모듈, 파트, 로직, 기능 등 테스트 할 수 있다.
 *      - 사전 검증 가능, CI 중에 자동 테스트 가능함 (문제 있는 경우 git에 반영 안됨)
 *      - 서버는 중단 후 작동, 다시 작동 시에는 적용 클래스 교체 필요
 */

@SpringBootTest
class DemoexApplicationTests {
    // @Test : 이 어노테이션이 붙은 메소드는 모두 테스트용 메소드임, 각각 작동
    @Test
    void contextLoads() {
        // 롬복 작동 테스트
        // 전통적인 객체 생성 방법

        // 빌더 패턴
        System.out.println("롬복 테스트");
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
    }

}
