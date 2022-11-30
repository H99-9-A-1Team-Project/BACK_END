package com.example.backend.consult.dto.response;

import com.example.backend.comment.dto.CommentResponseDto;
import com.example.backend.comment.model.Comment;
import com.example.backend.consult.model.AnswerState;
import com.example.backend.consult.model.Consult;
import com.example.backend.like.model.Like;
import com.example.backend.user.model.Realtor;
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
    private List<CommentResponseDto> comments;


}

