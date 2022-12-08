package com.example.backend.footsteps.model;

import com.example.backend.footsteps.model.FootstepsPost;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashMap;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Photo {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="footstepsPost_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private FootstepsPost footstepsPost;
    @Column
    private String sunImg;
    //습도/곰팡이
    @Column
    private String moldImg;
    //환기
    @Column
    private String ventImg;
    @Column
    private String waterImg;
    @Column
    private String ventilImg;
    @Column
    private String drainImg;
    //외풍
    @Column
    private String draftImg;    //파손
    @Column
    private String destroyImg;
    //다용도실
    @Column
    private String utiRoomImg;
    //방범창
    @Column
    private String securityWindowImg;
    //소음
    @Column
    private String noiseImg;
    //대출
    @Column
    private String loanImg;
    //cctv
    @Column
    private String cctvImg;
    //언덕
    @Column
    private String hillImg;
    //시장,마트
    @Column
    private String martImg;
    //병원
    @Column
    private String hospitalImg;
    //접근성
    @Column
    private String accessibilityImg;
    //주차장
    @Column
    private String parkImg;



}
