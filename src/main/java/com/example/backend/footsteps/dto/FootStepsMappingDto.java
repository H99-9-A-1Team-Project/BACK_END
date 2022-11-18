package com.example.backend.footsteps.dto;

import java.time.LocalDateTime;

public interface FootStepsMappingDto {

    String getId();
    String getTitle();
    double getCoordfx();
    double getCoordfy();
    LocalDateTime getCreate_Date();
    boolean getDestroy();
    boolean getDraft();
    boolean getDrain();
    String getExtra_Memo();
    boolean getHill();
    boolean getHospital();
    boolean getLoan();
    boolean getMart();
    boolean getMold();
    boolean getNoise();
    String getOption();
    boolean getPark();
    Long getPrice();
    String getReview();
    boolean getSecurity_Window();
    Long getSize();
    boolean getSun();
    boolean getUti_Room();
    boolean getVent();
    boolean getVentil();
    boolean getwater();
}
