package com.example.backend.consult.dto.request;

import com.example.backend.consult.model.AnswerState;
import com.example.backend.consult.model.Consult;
import com.example.backend.footsteps.model.FootstepsPost;
import com.example.backend.global.security.auth.UserDetailsImpl;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegisterConsultRequestDto {
    private String title;
    private double coordX;
    private double coordY;
    private boolean check1;
    private boolean check2;
    private boolean check3;
    private boolean check4;
    private boolean check5;
    private boolean check6;
    private String consultMessage;

    public Consult toConsultDto(RegisterConsultRequestDto dto, UserDetailsImpl userDetails) {
    return Consult.builder()
                .title(dto.getTitle())
                .coordX(dto.getCoordX())
                .coordY(dto.getCoordY())
                .check1(dto.isCheck1())
                .check2(dto.isCheck2())
                .check3(dto.isCheck3())
                .check4(dto.isCheck4())
                .check5(dto.isCheck5())
                .check6(dto.isCheck6())
                .consultMessage(dto.getConsultMessage())
                .createDate(LocalDateTime.now())
                .user(userDetails.getUser())
                .answerState(AnswerState.WAIT)
                .build();
    }
}
