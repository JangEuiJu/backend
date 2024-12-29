package org.example.demoex.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.demoex.entities.Post;
import org.example.demoex.entities.Review;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class ReviewDto {
    private int id;
    private String content;
    private LocalDateTime createDate;
    private Post post;
    public Review toEntity(){
        return Review.builder()
                .id(id)
                .content(content)
                .createDate(createDate)
                .post(post)
                .build();
    }
}
