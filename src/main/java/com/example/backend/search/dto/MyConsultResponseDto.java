package com.example.backend.search.dto;

import com.example.backend.comment.model.Comment;
import com.example.backend.consult.model.AnswerState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class MyConsultResponseDto {
    private String title;
    private String consultMessage;
    private AnswerState answerState;
    private LocalDateTime createDate;
    private String searchWord;
    private String comment;
}
