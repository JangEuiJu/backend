package org.example.demoex.services;

import lombok.RequiredArgsConstructor;
import org.example.demoex.dto.PostDto;
import org.example.demoex.dto.ReviewDto;
import org.example.demoex.entities.Review;
import org.example.demoex.repositories.PostRepository;
import org.example.demoex.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReviewService {
    // DI, 생성자 방식 구현
    @Autowired
    private final ReviewRepository reviewRepository;

    // 리뷰 등록
    public void create(PostDto postDto, String content) {
        // 1. 리뷰 엔티티 구성 (현재 Dto가 없음)
        Review review = Review.builder()
                .content(content)
                .createDate(LocalDateTime.now())
                .post(postDto.toEntity())
                .build();
        // 2. 레퍼지토리를 이용하여 등록
        reviewRepository.save(review);
    }
    
    public ReviewDto getOneReview(Integer id) {
        // id -> Review 엔티티
        // 데이터가 존재할때 처리 -> Review 엔티티 -> ReviewDto 반환
        Optional<Review> oReview = this.reviewRepository.findById(id);
        if (oReview.isPresent()) { // 리뷰가 존재하면
            Review review = oReview.get();
            return ReviewDto.builder()
                    .id(review.getId())
                    .content(review.getContent())
                    .createDate(review.getCreateDate())
                    .post(review.getPost())
                    .build();
        }
        // 없을 경우 -> 예외처리를 던지는 것으로 정리
        // throw new Exception();
        return null;
    }
    // 실제 테이블상 리뷰 삭제 처리
    public void delete(ReviewDto reviewDto) {
        this.reviewRepository.delete(reviewDto.toEntity());
    }
}