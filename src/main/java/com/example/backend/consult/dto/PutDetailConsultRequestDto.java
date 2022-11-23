package com.example.backend.consult.dto;

import com.example.backend.global.entity.AnswerState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PutDetailConsultRequestDto {
    public AnswerState answerState;

}
