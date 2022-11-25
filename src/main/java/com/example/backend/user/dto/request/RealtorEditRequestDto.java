package com.example.backend.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RealtorEditRequestDto {
    private String nickname;
    private String introMessage;
}
