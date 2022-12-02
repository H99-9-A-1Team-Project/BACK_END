package com.example.backend.user.dto.response;

import com.example.backend.user.model.Authority;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberResponseDto {
    private Long memberNo;

    private Authority role;

    private String email;

    private String nick;

    @JsonFormat(pattern = "yyyy년 MM월 dd일 E요일 a hh:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy년 MM월 dd일 E요일 a hh:mm:ss", timezone = "Asia/Seoul")
    private  LocalDateTime modifiedAt;


    @Builder
    public MemberResponseDto(Long memberNo, Authority role, String email, String nick,
                              LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.memberNo = memberNo;
        this.role = role;
        this.email = email;
        this.nick = nick;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}