package com.example.backend.footsteps.dto.request;

import com.example.backend.footsteps.model.FootstepsPost;
import com.example.backend.global.security.auth.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FootstepsRequstDto {
    private String title;

    private double coordFY;

    private double coordFX;
    //가격
    private Long price;
    //평수
    private Long size;
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

    public FootstepsPost toFootstepsPost(FootstepsRequstDto postRequestDto, UserDetailsImpl userDetails) {

        return FootstepsPost.builder()
                .title(postRequestDto.getTitle())
                .coordFY(postRequestDto.getCoordFY())
                .coordFX(postRequestDto.getCoordFX())
                .price(postRequestDto.getPrice())
                .size(postRequestDto.getSize())
                .review(postRequestDto.getReview())
                .sun(postRequestDto.isSun())
                .mold(postRequestDto.isMold())
                .vent(postRequestDto.isVent())
                .water(postRequestDto.isWater())
                .ventil(postRequestDto.isVentil())
                .drain(postRequestDto.isDrain())
                .draft(postRequestDto.isDraft())
                .extraMemo(postRequestDto.getExtraMemo())
                .option(postRequestDto.getOption())
                .destroy(postRequestDto.isDestroy())
                .utiRoom(postRequestDto.isUtiRoom())
                .securityWindow(postRequestDto.isSecurityWindow())
                .noise(postRequestDto.isNoise())
                .loan(postRequestDto.isLoan())
                .cctv(postRequestDto.isCctv())
                .hill(postRequestDto.isHill())
                .mart(postRequestDto.isMart())
                .hospital(postRequestDto.isHospital())
                .accessibility(postRequestDto.isAccessibility())
                .park(postRequestDto.isPark())
                .createDate(LocalDateTime.now())
                .user(userDetails.getUser())
                .build();
    }
}