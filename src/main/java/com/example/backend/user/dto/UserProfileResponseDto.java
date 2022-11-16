package com.example.backend.user.dto;

import com.example.backend.global.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileResponseDto {

    private String nickname;
    private int accountState;

    public UserProfileResponseDto(User user) {
        this.nickname = user.getNickname();
        this.accountState = user.getAuthority().getNum();
    }
}



