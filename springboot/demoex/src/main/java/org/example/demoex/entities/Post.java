package org.example.demoex.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 *  본 글에 대한 엔티티, postt 테이블 대변
 */

@Getter
@Setter
@NoArgsConstructor  // 기본 생성자
@Entity // 해당 어노테이션을 붙여야 entitiy가 된다
public class Post {
    // 컬럼 나열
    @Id
    // auto_increment, 자동 부여, 자동 증가, 중복 x
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 크기 : 256
    @Column(length = 256)
    private String subject;

    // 65536
    @Column(columnDefinition = "TEXT")
    private String content;

    // 생략
    private LocalDateTime createDate;

    // 본 글 : 리뷰 = 1 : N
    // mappedBy => FK 컬럼명
    // cascade = CascadeType.REMOVE => 본 글 삭제되면 관계된 리뷰 모두 삭제
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Review> reviews;


    // 전체 멤버를 구성(초기화)하는 생성자를 빌더패턴으로 사용가능하게 표현
    @Builder
    public Post(int id, String subject, String content, LocalDateTime createDate, List<Review> reviews) {
        this.id = id;
        this.subject = subject;
        this.content = content;
        this.createDate = createDate;
        this.reviews = reviews;
    }
}
