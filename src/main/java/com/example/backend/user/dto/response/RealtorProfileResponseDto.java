package com.example.backend.user.dto.response;

import com.example.backend.user.model.Realtor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RealtorProfileResponseDto {

    private int accountState;
    private String nickname;
    private String profile;
    private String introMessage;
    private String email;

    private Long likeCount;

    public RealtorProfileResponseDto(Realtor realtor) {
        this.introMessage = realtor.getIntroMessage();
        this.accountState = realtor.getAuthority().getNum();
        this.profile = realtor.getProfile();
        this.nickname = realtor.getNickname();
        this.email = realtor.getEmail();
        this.likeCount = realtor.getLikeCount();
    }
}