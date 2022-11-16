package com.example.backend.user.dto;

import com.example.backend.global.entity.Realtor;
import com.example.backend.global.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class RealtorProfileResponseDto {

    private int accountState;
    private String nickname;
    private String profile;
    private String introMessage;

    public RealtorProfileResponseDto(Realtor realtor) {
        this.introMessage = realtor.getIntroMessage();
        this.accountState = realtor.getAuthority().getNum();
        this.profile = realtor.getProfile();
        this.nickname = realtor.getNickname();
    }
}