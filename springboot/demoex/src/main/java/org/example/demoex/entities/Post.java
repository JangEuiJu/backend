package org.example.demoex.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 *  본 글에 대한 엔티티, postt 테이블 대변
 */

@Getter
@Setter
@Entity
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
}
