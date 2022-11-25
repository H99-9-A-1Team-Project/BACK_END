package com.example.backend.user.dto.response;

import com.example.backend.user.model.Realtor;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class RealtorListResponseDto {
    private String email;
    private String nickname;
    private String createDate;
    private String license;
    private Long accountCheck;

    public RealtorListResponseDto(Realtor realtor) {
        this.email = realtor.getEmail();
        this.nickname = realtor.getNickname();
        this.createDate = realtor.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        this.license = realtor.getLicense();
        this.accountCheck = realtor.getAccountCheck();
    }
}