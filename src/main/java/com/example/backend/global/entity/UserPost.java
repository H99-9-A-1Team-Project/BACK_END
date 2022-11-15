package com.example.backend.global.entity;

import com.example.backend.global.config.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;



@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class UserPost extends BaseTimeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne(cascade =  CascadeType.PERSIST,fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "member_id",nullable = false)
    private User user;

    //제목
    @Column(nullable = false)
    private String title;

    //상답 답변 유무
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AnswerState answerState;
    //상담 내용
    @Column(nullable = false)
    private String consultMessage;


    //x좌표
    @Column(nullable = false)
    private double coordX;
    //y좌표
    @Column(nullable = false)
    private double coordY;


    //CheckList
    //불법 건축물 확인
    @Column(nullable = false)
    private boolean check1;
    //층별 구조 및 용도
    @Column(nullable = false)
    private boolean check2;
    //소유자 현황
    @Column(nullable = false)
    private boolean check3;
    //준공시기
    @Column(nullable = false)
    private boolean check4;
    //등기정보
    @Column(nullable = false)
    private boolean check5;
    //보험유무
    @Column(nullable = false)
    private boolean check6;

}
