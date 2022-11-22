package com.example.backend.consult.dto;

import com.example.backend.global.config.auth.UserDetailsImpl;
import com.example.backend.global.entity.AnswerState;
import com.example.backend.global.entity.Consult;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegisterConsultDto {
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
}
