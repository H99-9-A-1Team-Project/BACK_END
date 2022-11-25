package com.example.backend.consult.dto.response;

import com.example.backend.comment.dto.CommentResponseDto;
import com.example.backend.consult.model.AnswerState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private List<CommentResponseDto> comments;


}
