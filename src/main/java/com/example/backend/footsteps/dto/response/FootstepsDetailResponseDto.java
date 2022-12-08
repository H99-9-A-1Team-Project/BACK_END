package com.example.backend.footsteps.dto.response;

import com.example.backend.footsteps.model.FootstepsPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FootstepsDetailResponseDto {
    private String title;

    private double coordFY;

    private double coordFX;
    //가격
    private String price;
    //평수
    private String size;
    //한줄평
    private String review;
    //햇빛
    private boolean sun;
    //습도/곰팡이
    private boolean mold;
    //환기
    private boolean vent;

    private boolean water;

    private boolean ventil;

    private boolean drain;
    //외풍
    private boolean draft;
    //매물추가 메모
    private String extraMemo;
    //옵션상태
    private String option;
    //파손
    private boolean destroy;
    //다용도실
    private boolean utiRoom;
    //방범창
    private boolean securityWindow;
    //소음
    private boolean noise;
    //대출
    private boolean loan;
    //cctv
    private boolean cctv;
    //언덕
    private boolean hill;
    //시장,마트
    private boolean mart;
    //병원
    private boolean hospital;
    //접근성
    private boolean accessibility;
    //주차장
    private boolean park;

    private String createdAt;
    private boolean YesOrNo;
    private String expenses;
    public FootstepsDetailResponseDto(FootstepsPost footstepsPost, boolean yesOrNo){
        this.title = footstepsPost.getTitle();
        this.coordFY = footstepsPost.getCoordFY();
        this.coordFX = footstepsPost.getCoordFX();
        this.price = footstepsPost.getPrice();
        this.size = footstepsPost.getSize();
        this.sun = footstepsPost.isSun();
        this.mold = footstepsPost.isMold();
        this.vent = footstepsPost.isVent();
        this.water = footstepsPost.isWater();
        this.ventil = footstepsPost.isVentil();
        this.drain = footstepsPost.isDrain();
        this.draft = footstepsPost.isDraft();
        this.extraMemo = footstepsPost.getExtraMemo();
        this.option = footstepsPost.getOption();
        this.utiRoom = footstepsPost.isUtiRoom();
        this.securityWindow = footstepsPost.isSecurityWindow();
        this.noise = footstepsPost.isNoise();
        this.loan = footstepsPost.isLoan();
        this.cctv = footstepsPost.isCctv();
        this.hill = footstepsPost.isHill();
        this.mart = footstepsPost.isMart();
        this.hospital = footstepsPost.isHospital();
        this.accessibility = footstepsPost.isAccessibility();
        this.park = footstepsPost.isPark();
        this.createdAt = footstepsPost.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        this.expenses =footstepsPost.getExpenses();
        this.review = footstepsPost.getReview();
        this.YesOrNo = yesOrNo;

    }
}
