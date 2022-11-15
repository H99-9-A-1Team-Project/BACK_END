package com.example.backend.footsteps.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FootstepsResponseDto {
    private String title;
    private List<PhotoResponseDto> postImgUrl;
}
