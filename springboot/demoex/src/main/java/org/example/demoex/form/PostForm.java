package org.example.demoex.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 *  글(본글)을 작성하는 입력폼에 대한 백엔드 레벨에서의 유효성 검사용 클래스
 *      - 제약 조건 부여, 값이 비워진 것 체크, 값의 길이 체크(테이블 상 컬럼의 크기 제약) 등
 *      - 본 글은 제목과 본문 2개만 체크
 */

@Getter
@Setter
public class PostForm {
    // 입력폼에 값이 비워져있으면 -> 서버로 전송된 후 체크 -> 에러 구성 -> 응답 -> 클라이언트 노출
    @NotEmpty(message = "제목은 반드시 입력해야 하는 필수 항목입니다.")    // 에러메세지 설정
    @Size(max = 256)    // 엔티티와 동일 크기 지정
    private String subject;

    @NotEmpty(message = "본문은 반드시 입력해야 하는 필수 항목입니다.")
    private String content;
}
