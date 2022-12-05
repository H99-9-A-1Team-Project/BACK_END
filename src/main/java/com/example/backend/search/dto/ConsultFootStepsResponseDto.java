package com.example.backend.search.dto;

import com.example.backend.consult.model.AnswerState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ConsultFootStepsResponseDto {
    private String title;
    private String review;
    private AnswerState answerState;
    private boolean overLab;
}
