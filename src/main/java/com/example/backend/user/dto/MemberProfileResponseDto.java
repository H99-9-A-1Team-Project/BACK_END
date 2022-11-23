package com.example.backend.user.dto;

import com.example.backend.global.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberProfileResponseDto {

    private String nickname;
    private int accountState;
    private String email;
    private int profileImg;

    public MemberProfileResponseDto(Member member) {
        this.nickname = member.getNickname();
        this.accountState = member.getAuthority().getNum();
        this.email = member.getEmail();
        this.profileImg = member.getProfileImg();
    }
}




