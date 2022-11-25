package com.example.backend.consult.dto.response;

import com.example.backend.comment.model.Comment;
import com.example.backend.consult.model.AnswerState;
import com.example.backend.consult.model.Consult;
import com.example.backend.like.model.Like;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

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
    private List<Comment> comments;


    public DetailConsultResponseDto(Consult consult, List<Comment> comments, List<Boolean> checks){
        this.Id = consult.getId();
        this.title = consult.getTitle();
        this.coordY = consult.getCoordY();
        this.coordX = consult.getCoordX();
        this.answerState = consult.getAnswerState();
        this.checks = checks;
        this.comments = comments;
        this.createdAt = consult.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));

    }
}
