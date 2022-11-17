package com.example.backend.footsteps.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FootstepsRequstDto {
    private String title;
    //가격
    private Long price;
    //평수
    private int size;
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
}
