package com.example.backend.user.model;


import com.example.backend.user.dto.request.editUserInfoRequestDto;
import com.example.backend.user.dto.request.SignUpMemberRequestDto;
import com.example.backend.user.dto.request.SignUpRealtorRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 30)
    private String nickname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @DateTimeFormat
    private LocalDateTime createdAt; // 날짜

    @PrePersist // DB에 INSERT 되기 직전에 실행. 즉 DB에 값을 넣으면 자동으로 실행됨
    public void createdAt() {
        this.createdAt = LocalDateTime.now();
    }

    public User(editUserInfoRequestDto nicknameRequestDto) {
        this.nickname = nicknameRequestDto.getNickname();
    }

    public User(SignUpRealtorRequestDto dto){
        this.email = dto.getEmail();
        this.password = dto.getPassword();
        this.nickname = dto.getNickname();
        this.authority = Authority.ROLE_REALTOR;
    }

    public User(SignUpMemberRequestDto signUpMemberRequestDto) {
        this.email = signUpMemberRequestDto.getEmail();
        this.password = signUpMemberRequestDto.getPassword();
        this.nickname = signUpMemberRequestDto.getNickname();
        this.authority = Authority.ROLE_USER;
    }

    public void updateNickname(editUserInfoRequestDto nicknameRequestDto) {
        this.nickname = nicknameRequestDto.getNickname();
    }
}
