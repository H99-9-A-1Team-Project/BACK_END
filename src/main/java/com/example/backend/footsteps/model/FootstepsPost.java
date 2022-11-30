package com.example.backend.footsteps.model;

import com.example.backend.consult.model.Photo;
import com.example.backend.footsteps.dto.request.FootstepsRequstDto;
import com.example.backend.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class FootstepsPost {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "footstepsPost",fetch = FetchType.LAZY)
    private List<Photo> photos= new ArrayList<>();

    @Column
    private String title;

    @Column
    private double coordFY;

    @Column
    private double coordFX;
    //가격
    @Column
    private Long price;
    //평수
    @Column
    private Long size;
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
    @Column
    private Long expenses;
    @DateTimeFormat
    private LocalDateTime createDate; // 날짜

    @PrePersist // DB에 INSERT 되기 직전에 실행. 즉 DB에 값을 넣으면 자동으로 실행됨
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

    public void updatePost(FootstepsRequstDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.coordFY = postRequestDto.getCoordFY();
        this.coordFX = postRequestDto.getCoordFX();
        this.price = postRequestDto.getPrice();
        this.size = postRequestDto.getSize();
        this.review = postRequestDto.getReview();
        this.sun = postRequestDto.isSun();
        this.mold = postRequestDto.isMold();
        this.vent = postRequestDto.isVent();
        this.water = postRequestDto.isWater();
        this.ventil = postRequestDto.isVentil();
        this.drain = postRequestDto.isDrain();
        this.draft = postRequestDto.isDraft();
        this.extraMemo = postRequestDto.getExtraMemo();
        this.option = postRequestDto.getOption();
        this.destroy = postRequestDto.isDestroy();
        this.utiRoom = postRequestDto.isUtiRoom();
        this.securityWindow = postRequestDto.isSecurityWindow();
        this.noise = postRequestDto.isNoise();
        this.loan = postRequestDto.isLoan();
        this.cctv = postRequestDto.isCctv();
        this.hill = postRequestDto.isHill();
        this.mart = postRequestDto.isMart();
        this.hospital = postRequestDto.isHospital();
        this.accessibility = postRequestDto.isAccessibility();
        this.park = postRequestDto.isPark();
        this.expenses = postRequestDto.getExpenses();
    }
}
