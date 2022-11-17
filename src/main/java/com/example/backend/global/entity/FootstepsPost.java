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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;


    @OneToMany(mappedBy = "footstepsPost",fetch = EAGER)
    private List<Photo> photos= new ArrayList<>();
    //제목
    @Column
    private String title;
    //가격
    @Column
    private Long price;
    //평수
    @Column
    private int size;
    //한줄평
    @Column
    private String review;
    //햇빛
    @Column
    private boolean sun;
    //습도/곰팡이
    @Column
    private boolean mold;
    //환기
    @Column
    private boolean vent;
    @Column
    private boolean water;
    @Column
    private boolean ventil;
    @Column
    private boolean drain;
    //외풍
    @Column
    private boolean draft;
    //매물추가 메모
    @Column
    private String extraMemo;
    //옵션상태
    @Column
    private String option;
    //파손
    @Column
    private boolean destroy;
    //다용도실
    @Column
    private boolean utiRoom;
    //방범창
    @Column
    private boolean securityWindow;
    //소음
    @Column
    private boolean noise;
    //대출
    @Column
    private boolean loan;
    //cctv
    @Column
    private boolean cctv;
    //언덕
    @Column
    private boolean hill;
    //시장,마트
    @Column
    private boolean mart;
    //병원
    @Column
    private boolean hospital;
    //접근성
    @Column
    private boolean accessibility;
    //주차장
    @Column
    private boolean park;


}
