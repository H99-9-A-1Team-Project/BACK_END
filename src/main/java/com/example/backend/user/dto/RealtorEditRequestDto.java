package com.example.backend.user.dto;

import com.example.backend.global.entity.Realtor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RealtorEditRequestDto {
    private String nickname;
    private String introMessage;
}
