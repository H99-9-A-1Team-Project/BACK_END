package com.example.backend.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ConsultFootStepsResponseDto {
    private Long id;
    private String title;
    private String review;
    private int overLab;
    private double coordX;
    private double coordY;
    private String thumbnail;
}
