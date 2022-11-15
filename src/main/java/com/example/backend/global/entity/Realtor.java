package com.example.backend.global.entity;

import com.example.backend.user.dto.IntroMessageDto;
import com.example.backend.user.dto.RealtorApproveDto;
import com.example.backend.user.dto.SignUpRealtorRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Realtor extends User {

    @Column
    private String introMessage;

    @Column
    private Long accountCheck;

    @Column
    private String profile;

    public Realtor(SignUpRealtorRequestDto dto) {
        super(dto);
        this.profile = dto.getProfile();
        this.accountCheck = 0L;
    }

    public void update(RealtorApproveDto dto) {
        this.accountCheck = dto.getAccountCheck();
    }

    public void update(IntroMessageDto dto) {
        this.introMessage = dto.getIntroMessage();
    }
}
