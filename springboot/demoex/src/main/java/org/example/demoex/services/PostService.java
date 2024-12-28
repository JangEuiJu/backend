package org.example.demoex.services;

import org.example.demoex.dto.PostDto;
import org.example.demoex.entities.Post;
import org.example.demoex.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 서비스, 비지니스 로직 처리, 주로 SQL 실행 명령을 내리는 곳
 */

@Service
public class PostService {
    // DI (의존성 주입, 유일한 객체 1개를 가져와서 사용하도록 조치)
    @Autowired
    private PostRepository postRepository;
    
    // 레퍼지토리(SQL을 가지고 있음)을 이용하여 모든 게시물을 가져온다
    // Post 객체(엔티티, 데이터 1개)를 PostDto로 변환하여 List에 담아서 반환 -> 보안
    public List<PostDto> getAllPost() {
        // 1. 레퍼지토리를 통해서 모든 게시물을 가져온다.
        //      1-1. 레퍼지토리? -> DI 처리 필요
        //      1-2. findAll : 사전에 준비된 메소드, 모든 데이터를 가져온다
        //           내부적 : select * from post; 수행된 것으로 판단
        List<Post> posts = postRepository.findAll();

        // 2. 결과물을 담을 자료구조를 준비 -> List<PostDto>
        List<PostDto> postDtos = new ArrayList<>();

        // 3. 순환하면서 Post->PostDto로 데이터를 교환하여 List에 담는다
        for (Post post : posts) {
            postDtos.add(
                    PostDto.builder()
                            .id(post.getId())
                            .subject(post.getSubject())
                            .content(post.getContent())
                            .createDate(post.getCreateDate())
                            .reviews(post.getReviews())
                            .build());
        }

        // 4. 반환
        return postDtos;
    }
}
