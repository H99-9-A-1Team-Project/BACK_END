package com.example.backend.footsteps.model;

import com.example.backend.footsteps.model.FootstepsPost;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
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

    public Photo(String sunImg,String moldImg,String ventImg,String waterImg ,String ventilImg,String drainImg,String draftImg,String destroyImg, String utiRoomImg,String securityWindowImg,
                 String noiseImg,String loanImg,String cctvImg,String hillImg,String martImg,String hospitalImg,String accessibilityImg, String parkImg, FootstepsPost post){
        this.sunImg = sunImg;
        this.moldImg =moldImg;
        this.ventImg = ventImg;
        this.waterImg = waterImg;
        this.ventilImg =ventilImg;
        this.drainImg = drainImg;
        this.draftImg = draftImg;
        this.destroyImg = destroyImg;
        this.utiRoomImg = utiRoomImg;
        this.securityWindowImg = securityWindowImg;
        this.noiseImg = noiseImg;
        this.loanImg =loanImg;
        this.cctvImg =cctvImg;
        this.hillImg =hillImg;
        this.martImg = martImg;
        this.hospitalImg =hospitalImg;
        this.accessibilityImg = accessibilityImg;
        this.parkImg =parkImg;
        this.footstepsPost = post;
    }
}
