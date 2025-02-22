package org.example.demoex.controllers;

import jakarta.validation.Valid;
import org.example.demoex.dto.PostDto;
import org.example.demoex.form.PostForm;
import org.example.demoex.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * - 글쓰기, 글목록, 글 수정, 글 삭제 등 url 제공
 * - 요청/응답 처리
 */

@RequestMapping("/post") // url prefix 설정
@Controller
public class PostController {
    // (*매우 중요)DI 구성 3가지 방식
    // (*)@Autowired 사용, 생성자 함수 활용, setter 활용
    @Autowired
    private PostService postService;    // 서비스 객체를 가져오는 것

    // ~/post/list, get 방식 준비
    @GetMapping("/list")
    public String list(Model model) {
        // 1. 서비스(PostService)를 통해서 쿼리가 실행이 되고(내부적), 그 결과를 가져온다
        //    서비스 객체를 가져와서 -> 매번 가져온다? -> 객체가 매번 생성된다? -> n개
        //    (*)객체가 한번만 만들어지고 => 요청할 때마다 그 객체가 와서 작업되어 진다면? -> 1개
        //    싱글톤 디자인 패턴!! 어떻게 제공할 것인가? -> SB에서는 DI(의존성 주입) 개념 도입 -> 해결
        //    postService.xxxx() 바로 사용 가능(이미 객체는 만들어져 있다)

        // 서비스의 getAllPost() 메소드를 호출해서, 모든 게시물을 가져와서 DTO에 교환해서 받을것이다!!
        // DTO 생성
        // 향후 페이징 기능 추가
        List<PostDto> posts = postService.getAllPost(); // 모든 post의 내용 가져오기
        // 디비에서 가져온 데이터 확인
        for (PostDto post : posts) {
            System.out.println(post.toString());
        }

        // 2. 조회 결과는 타임리프 템플릿 엔진에 전달하여, 동적으로 html 구성 한다(랜더링)
        //    Model 객체를 활용하여 데이터를 세팅 후 전달한다
        //    1. 매개변수에 모델 객체 추가
        //    2. 코드 내부에서 모델 객체에 데이터 추가
        model.addAttribute("posts", posts); // 게시물 데이터 전달
        model.addAttribute("dummy", "hello"); // 더미

        // 3. 응답한다
        return "board/post_list"; // resource/templates/test/post_list.html 읽어서 렌더링

    }
    
    // ~/post/create, get 방식
    @GetMapping("/create")
    public String create() {
        return "board/post_form";
    }

    // ~/post/create2, get 방식
    @GetMapping("/create2")
    public String create2(PostForm postForm) {
        // PostForm을 매개변수로 전달받음으로써, html 랜더링시 세팅 가능
        return "board/post_form";
    }

    // ~/post/create, post 방식
    // 자바의 오버로딩 => 매개변수를 다르게 사용해서 다른 메소드처럼 관리
    // 매개변수명은 <input name='값'>의 '값'을 변수명으로 사용 (중요!)
    @PostMapping("/create")
    public String create(@RequestParam String subject,
                         @RequestParam String content) {
        // 1. 클라이언트가 전송한 데이터 추출
        System.out.println(subject + " " + content);
        // 2. 서비스를 통해서 데이터 입력 처리 요청
        //    입력값 -> PostDto 세팅 -> 서비스 처리 함수(미구현)에 전달
        this.postService.create(PostDto.builder()
                .subject(subject)
                .content(content)
                .createDate(LocalDateTime.now()) // 서버의 시간
                .build());
        // 3. 처리 결과를 받아서 후속처리(잘 되었는지, 오류 났는지 등등) -> 생략
        // 4. 글이 하나 추가 되었으므로, 다시 목록으로 포워딩!!
        return "redirect:/post/list"; // 특정 페이지로 자동 이동
    }

    // ~/post/create2, post 방식, 유효성 검사 반영
    // PostForm 객체가 데이터를 들고 요청을 타고 전달됨 (마치 통신용 DTO처럼 작동됨)
    // @Valid는 검증 수행을 요청하는 어노테이션
    // @Valid 사용 -> 2번 인자로 BindingResult를 파라미터를 동반 -> 동적으로 검증 결과를 반영한 객체
    @PostMapping("/create2")
    public String create2(@Valid PostForm postForm, BindingResult bindingResult) {
        // 0. 검증 결과에 문제가 있는가?
        if (bindingResult.hasErrors()) {
            // 존재하면 -> 다시 입력폼으로 화면을 돌린다 -> PostForm 객체에 에러가 세팅된 상태 -> 에러 표시
            // 글 등록 실패 -> 액션 수정 -> 다시 글 등록 시도
            return "board/post_form";
        }
        // 1. 클라이언트가 전송한 데이터 추출
        System.out.println(postForm.getSubject() + " " + postForm.getContent());
        // 2. 서비스를 통해서 데이터 입력 처리 요청
        //    입력값 -> PostDto 세팅 -> 서비스 처리 함수(미구현)에 전달
        this.postService.create(PostDto.builder()
                .subject(postForm.getSubject())
                .content(postForm.getContent())
                .createDate(LocalDateTime.now()) // 서버의 시간
                .build());
        // 3. 처리 결과를 받아서 후속처리(잘 되었는지, 오류 났는지 등등) -> 생략
        // 4. 글이 하나 추가 되었으므로, 다시 목록으로 포워딩!!
        return "redirect:/post/list"; // 특정 페이지로 자동 이동
    }

    /**
     * id 번호에 따라 글을 조회(DB 연동)하여, 상세보기 처리
     * @return
     */
    @GetMapping("/detail/{id}")
    public String detail(Model model,
                         @PathVariable("id") Integer id) {
        // 1. 파라미터 추출(클라이언트->서버 데이터 전송) => id값 추출, @PathVariable
        System.out.println("상세보기 : " + id);
        // 2. id를 이용하여 PostDto(Post와 교환된) 1개를 추출 -> 서비스 요청
        PostDto post = this.postService.getOnePost(id); // id : Post 데이터를 구분하는 PK
        // 3. PostDto를 Model에 적용하여 타임리프에서 랜더링 요청
        model.addAttribute("post", post);
        // 4. 응답 html 완료(상세 보기 완성)
        return "board/post_detail";
    }

    // 본 글 수정하기 화면
    @GetMapping("/modify/{id}")
    public String modify(PostForm postForm, @PathVariable Integer id) {
        // 1. id에 해당되는 데이터 가져오기
        // id -> 쿼리수행(서비스 <-> 레포지토리 <-> jpa <-> db, 엔티티, dto 활용)
        PostDto postDto = this.postService.getOnePost(id);

        // 2. PostForm 객체에 데이터를 세팅 -> 랜더링시 적용 -> 이미 작성해둠
        //    @Vaild 미사용-> 현재 요구사항은 검증x, 화면세팅이므로 (통신용 dto로 사용)
        postForm.setSubject(postDto.getSubject());
        postForm.setContent(postDto.getContent());
        return "board/post_form";
    }

    @PostMapping("/modify/{id}")
    public String modify(@Valid PostForm postForm,
                         BindingResult bindingResult,
                         @PathVariable Integer id) {
        // 0. 검증 결과에 문제 있는가?
        if (bindingResult.hasErrors()) {
            // 존재하면 => 다시 입력폼으로 화면을 돌린다 -> PostForm 객체에 에러가 세팅된 상태 -> 에러가 표시됨
            // 글등록 실패 -> 사용자는 액션 수정 => 다시 글등록 시도
            return "board/post_form";
        }
        // 1. 클라이언트가 전송한 데이터 추출
        System.out.println(postForm.getSubject() + " " + postForm.getContent());
        // 2. 서비스를 통해서 데이터 입력 처리 요청
        //    id -> 조회 -> dto (결과가 없다면 리젝 가능하다) -> 내용수정 -> 반영
        //  2-1. id를 이용하여 실제 저장된 내용을 가져옴 (보안 이슈)
        PostDto postDto = this.postService.getOnePost( id );
        // 염두해 두고 진행
//        if(postDto != null ) {
//            // 내용이 없거나 등등 -> 잘못 전달된 수정 요청!! -> 거절
//        }
        // 2-2. 정상적인 글이다, 변경된 내용만 변경, 수정 시간 미고려함
        postDto.setSubject(postForm.getSubject());
        postDto.setContent(postForm.getContent());
        // 2-3. 수정
        this.postService.modify(postDto);
        // 3. 처리 결과를 받아서 후속처리(잘 되었는지, 오류 났는지 등등) -> 생략
        // 4. 글수정 => 글 상세보기로 이동
        //    순환 ; 상세보기 -> 글수정 -> 수정처리 -> 상세보기
        return "redirect:/post/detail/" + id; // 특정 페이지로 자동 이동!!
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        // JS로 한번 더 컨펌하는 단계는 일단 생략
        // 1. id를 기반으로 dto 획득
        PostDto postDto = this.postService.getOnePost(id);
        // 2. postDto 정상 여부 체크, 값이 존재하는가? -> 생략
        // 3. 삭제
        this.postService.delete(postDto);
        return "redirect:/post/list"; // 게시판 목록
    }

}
