package com.example.backend.user.model;

import com.example.backend.user.dto.request.editUserInfoRequestDto;
import com.example.backend.user.dto.request.SignUpMemberRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Member extends User{

    @Column
    private int profileImg;


    public Member(SignUpMemberRequestDto signUpMemberRequestDto) {
        super(signUpMemberRequestDto);
        this.profileImg = 1;
    }

    public void updateProfileImage(editUserInfoRequestDto nicknameRequestDto) {
        this.profileImg = nicknameRequestDto.getProfileImg();
    }
}
