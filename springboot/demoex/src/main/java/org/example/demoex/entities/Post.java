package org.example.demoex.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 *  본 글에 대한 엔티티, postt 테이블 대변
 */

@Getter
@Setter
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
}
