package com.example.backend.consult.dto;

import com.example.backend.comment.dto.CommentResponseDto;
import com.example.backend.global.entity.AnswerState;
import com.example.backend.global.entity.Comment;
import com.example.backend.global.entity.Consult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

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

    private List<String> comments;

    private AnswerState answerState;
    private String createdAt;

    public UserAllConsultResponseDto(Consult consult){
        this.id = consult.getId();
        this.title = consult.getTitle();
        this.coordY = consult.getCoordY();
        this.coordX = consult.getCoordX();
        this.consultMessage = consult.getConsultMessage();
        this.comments = consult.getCommentList()
                .stream()
                .map(Comment::getContent)
                .collect(Collectors.toList());
        this.answerState = consult.getAnswerState();
        this.createdAt = consult.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }
}
