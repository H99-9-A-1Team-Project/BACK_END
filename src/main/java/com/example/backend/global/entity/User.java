package com.example.backend.global.entity;


import com.example.backend.user.dto.SignUpRealtorRequestDto;
import com.example.backend.user.dto.SignUpRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
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

    @Column(nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @DateTimeFormat
    private LocalDateTime createDate; // 날짜


    @PrePersist // DB에 INSERT 되기 직전에 실행. 즉 DB에 값을 넣으면 자동으로 실행됨
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

    @Builder
    public User(String email, String password, String nickname,Authority authority) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.authority = authority;
    }

    public User(SignUpRequestDto signUpRequestDto) {
        this.email = signUpRequestDto.getEmail();
        this.password = signUpRequestDto.getPassword();
        this.nickname = signUpRequestDto.getNickname();
        this.authority = Authority.ROLE_USER;
    }

    public User(SignUpRealtorRequestDto dto){
        this.email = dto.getEmail();
        this.password = dto.getPassword();
        this.nickname = dto.getNickname();
        this.authority = Authority.ROLE_REALTOR;

    }


}
