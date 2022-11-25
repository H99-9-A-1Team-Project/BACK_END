package com.example.backend.consult.dto.request;

import com.example.backend.consult.model.AnswerState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PutDetailConsultRequestDto {
    public AnswerState answerState;

}
