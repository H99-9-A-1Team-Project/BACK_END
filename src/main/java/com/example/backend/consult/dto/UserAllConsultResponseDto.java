package com.example.backend.consult.dto;

import com.example.backend.global.entity.AnswerState;
import com.example.backend.global.entity.Consult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAllConsultResponseDto {
    private Long id;
    private String title;
    private double coordX;
    private double coordY;
    private String consultMessage;

    private AnswerState answerState;
    private LocalDateTime createdAt;

    public UserAllConsultResponseDto(Consult consult){
        this.id = consult.getId();
        this.title = consult.getTitle();
        this.coordY = consult.getCoordY();
        this.coordX = consult.getCoordX();
        this.consultMessage = consult.getConsultMessage();
        this.answerState = consult.getAnswerState();
        this.createdAt = consult.getCreateDate();
    }
}
