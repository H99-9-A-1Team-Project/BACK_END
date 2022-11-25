package com.example.backend.consult.dto.request;

import lombok.Data;

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
}
