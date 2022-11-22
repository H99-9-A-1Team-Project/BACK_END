package com.example.backend.consult.dto;

import com.example.backend.comment.dto.CommentResponseDto;
import com.example.backend.global.entity.AnswerState;
import com.example.backend.global.entity.Comment;
import com.example.backend.global.entity.Consult;
import com.example.backend.global.entity.User;
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
    private boolean check1;
    private boolean check2;
    private boolean check3;
    private boolean check4;
    private boolean check5;
    private boolean check6;

    private AnswerState answerState;
    private String createdAt;
    private List<CommentResponseDto> comments;

    public void DetailConsultResponseDto(Consult consult){
        this.Id = consult.getId();
        this.title = consult.getTitle();
        this.coordX = consult.getCoordX();
        this.coordY = consult.getCoordY();
        this.answerState = consult.getAnswerState();
        this.check1 = consult.isCheck1();
        this.check2 = consult.isCheck2();
        this.check3 = consult.isCheck3();
        this.check4 = consult.isCheck4();
        this.check5 = consult.isCheck5();
        this.check6 = consult.isCheck6();
    }
}
