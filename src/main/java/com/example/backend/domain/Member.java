package com.example.backend.domain;

import com.example.backend.domain.base.BaseTimeEntity;
import com.example.backend.dto.request.MemberRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String profileImg;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MemberRole role;


    public Member(MemberRequestDto memberReqDto) {
        this.username = memberReqDto.getUsername();
        this.password = memberReqDto.getPassword();
        this.role = MemberRole.MEMBER;
    }

    public boolean validatePassword(PasswordEncoder passwordEncoder, String password){
        return passwordEncoder.matches(password, this.password);
    }
}
