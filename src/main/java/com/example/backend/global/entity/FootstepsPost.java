package com.example.backend.global.entity;

import com.example.backend.global.config.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.EAGER;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class FootstepsPost extends BaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne(cascade =  CascadeType.PERSIST,fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "member_id",nullable = false)
    private User user;

    @OneToMany(mappedBy = "footstepsPost",fetch = EAGER)
    private List<Photo> photos= new ArrayList<>();
    //제목
    @Column(nullable = false)
    private String title;

    //상답 답변 유무
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AnswerState answerState;
    //가격
    @Column(nullable = false)
    private Long price;
    //평수
    @Column(nullable = false)
    private double size;
    //한줄평
    @Column
    private String review;
    //햇빛
    @Column
    private String sun;
    //습도/곰팡이
    @Column
    private String mold;
    //옵션상태
    @Column
    private String option;
    //환기
    @Column
    private String ventil;
    //파손
    @Column
    private String destroy;
    //임대인
    @Column
    private String owenr;
    //방범창
    @Column
    private String securityWindow;
    //소음
    @Column
    private String noise;
    //대출
    @Column
    private String loan;
    //외풍
    @Column
    private String draft;
    //다용도실
    @Column
    private String utiRoom;
    //엘리베이터 유무
    @Column
    private boolean elevator;
    //매물추가 메모
    @Column
    private String extraMemo;

    //집주변
    //언덕
    @Column
    private String hll;
    //편의점
    @Column
    private String store;
    //시장,마트
    @Column
    private String mart;
    //접근성
    @Column
    private String accessibility;
    //병원
    @Column
    private String hospital;
    //방범시설
    @Column
    private String securityArea;
    //약국
    @Column
    private String pharmacy;
    //지하철역
    @Column
    private String subway;
    //버스정류장
    @Column
    private String bus;
    //주차장
    @Column
    private String park;
    //집 주변 추가메모
    @Column
    private String homeAroundMemo2;


}
