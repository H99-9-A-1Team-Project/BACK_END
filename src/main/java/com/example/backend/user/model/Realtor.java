package com.example.backend.user.model;

import com.example.backend.user.dto.request.RealtorApproveRequestDto;
import com.example.backend.user.dto.request.RealtorEditRequestDto;
import com.example.backend.user.dto.request.SignUpRealtorRequestDto;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Realtor extends User {

    @Column(length = 1000)
    @Lob
    private String introMessage;

    @Column
    private AccountCheck accountCheck;

    @Column
    private String license;

    @Column
    private String profile;

    @Column
    private Long likeCount;

    public Realtor(SignUpRealtorRequestDto dto) {
        super(dto);
        this.license = dto.getLicense();
        this.accountCheck = AccountCheck.APPROVE_WAIT;
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
    public void update(Long likeCount){
        this.likeCount = likeCount;
    }
}
