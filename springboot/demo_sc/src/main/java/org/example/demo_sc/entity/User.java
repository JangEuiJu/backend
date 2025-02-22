package org.example.demo_sc.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * 테이블과 1대 1로 연결된 클래스, 1개 객체는 데이터 1개와 연동
 */

@Table(name = "userTbl") // 엔티티의 이름과 실제 테이블명을 다르게 하고 싶다면
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자
@Entity
// 인증 관련 UserDetails를 적용 => 스프링 시큐리티 정책
public class User implements UserDetails {
    // 단순하게 정리 : Long id, String email, String password (암호화)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)    // name="변경된 컬럼명"
    private String email;

    @Column(nullable = false)
    private String password;

    // 생성자 + 빌더
    @Builder
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // 7개의 추상 메소드를 재정의하여 interface를 구현
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("normal_user")); // 임시 롤 적용
    }

    @Override
    public String getUsername() {
        // 사용자명이 없으므로 email로 대체
        return email;
    }

    @Override
    public String getPassword() {
        // 사용자의 암호화된 암호 대체 처리
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
