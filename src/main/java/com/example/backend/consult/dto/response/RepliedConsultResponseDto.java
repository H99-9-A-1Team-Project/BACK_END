package com.example.backend.consult.dto.response;

import com.example.backend.consult.model.AnswerState;
import com.example.backend.consult.model.Consult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RepliedConsultResponseDto {
    private Long id;
    private String title;
    private String consultMessage;
    private String answerMessage;

    private AnswerState answerState;
    private String createdAt;

    public RepliedConsultResponseDto(Consult consult, String content){
        this.id = consult.getId();
        this.title = consult.getTitle();
        this.consultMessage = consult.getConsultMessage();
        this.answerMessage = content;
        this.answerState = consult.getAnswerState();
        this.createdAt = consult.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }
}
