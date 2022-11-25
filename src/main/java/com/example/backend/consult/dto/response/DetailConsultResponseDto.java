package com.example.backend.consult.dto.response;

import com.example.backend.comment.model.Comment;
import com.example.backend.consult.model.AnswerState;
import com.example.backend.consult.model.Consult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailConsultResponseDto {
    private Long Id;
    private String title;
    private double coordX;
    private double coordY;
    private String consultMessage;
    private List<Boolean> checks;

    private AnswerState answerState;
    private String createdAt;
    private Comment comments;

public DetailConsultResponseDto(Consult consult, Comment comments, List<Boolean> checks){
    DetailConsultResponseDto.builder()
            .Id(consult.getId())
            .title(consult.getTitle())
            .coordX(consult.getCoordX())
            .coordY(consult.getCoordY())
            .answerState(consult.getAnswerState())
            .checks(checks)
            .comments(comments)
            .createdAt(consult.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
            .build();
}
}
