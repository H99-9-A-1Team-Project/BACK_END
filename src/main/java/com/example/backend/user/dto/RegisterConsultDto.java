package com.example.backend.user.dto;

import lombok.Data;

@Data
public class RegisterConsultDto {
    private String title;
    private Double coordX;
    private Double coordY;
    private Boolean check1;
    private Boolean check2;
    private Boolean check3;
    private Boolean check4;
    private Boolean check5;
    private Boolean check6;
    private String consultMessage;
}
