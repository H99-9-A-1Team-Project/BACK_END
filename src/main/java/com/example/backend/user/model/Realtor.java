package com.example.backend.user.model;

import com.example.backend.user.dto.request.RealtorApproveRequestDto;
import com.example.backend.user.dto.request.RealtorEditRequestDto;
import com.example.backend.user.dto.request.SignUpRealtorRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Realtor extends User {

    @Column(length = 1000)
    @Lob
    private String introMessage;

    @Column
    private Long accountCheck;

    @Column
    private String license;

    @Column
    private String profile;


    public Realtor(SignUpRealtorRequestDto dto) {
        super(dto);
        this.license = dto.getLicense();
        this.accountCheck = 0L;
    }

    public void update(RealtorApproveRequestDto dto) {
        this.accountCheck = dto.getAccountCheck();
    }

    public void update(RealtorEditRequestDto dto, String imageUrl) {
        this.introMessage = dto.getIntroMessage();
        this.setNickname(dto.getNickname());
        this.profile = imageUrl;
    }

    public void update(RealtorEditRequestDto dto) {
        this.introMessage = dto.getIntroMessage();
        this.setNickname(dto.getNickname());
    }
}
