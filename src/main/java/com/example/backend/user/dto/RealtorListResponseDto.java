package com.example.backend.user.dto;

import com.example.backend.global.entity.Realtor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RealtorListResponseDto {
    private String email;
    private String nickname;
    private LocalDateTime createDate;
    private String profile;
    private Long check;

    public RealtorListResponseDto(Realtor realtor) {
        this.email = realtor.getEmail();
        this.nickname = realtor.getNickname();
        this.createDate = realtor.getCreateDate();
        this.profile = realtor.getIntroMessage();
        this.check = realtor.getAccountCheck();
    }
}
