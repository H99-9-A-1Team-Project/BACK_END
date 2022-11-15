package com.example.backend.global.entity;

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
    private String profile;

    @Column
    private Long check;

    public Realtor(SignUpRealtorRequestDto dto) {
        super(dto);
        this.profile = dto.getProfile();
        this.check = 0L;
    }

    public void update(RealtorApproveDto dto) {
        this.check = dto.getAccountCheck();
    }
}
