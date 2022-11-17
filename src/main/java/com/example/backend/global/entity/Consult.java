package com.example.backend.global.entity;

import com.example.backend.consult.dto.RegisterConsultDto;
import com.example.backend.global.config.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Consult extends BaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column//(name="post_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Double coordX;

    @Column(nullable = false)
    private Double coordY;

    @Column(nullable = false)
    private boolean check1;

    @Column(nullable = false)
    private boolean check2;

    @Column(nullable = false)
    private boolean check3;

    @Column(nullable = false)
    private boolean check4;

    @Column(nullable = false)
    private boolean check5;

    @Column(nullable = false)
    private boolean check6;

    @Column(nullable = false)
    private String consultMessage;

    @Column(nullable = false)
//    @Enumerated(EnumType.STRING)
    private AnswerState answerState;
    @DateTimeFormat
    private LocalDateTime createDate; // 날짜

    @PrePersist // DB에 INSERT 되기 직전에 실행. 즉 DB에 값을 넣으면 자동으로 실행됨
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

    public Consult(RegisterConsultDto registerConsultDto) {
        this.title = registerConsultDto.getTitle();
        this.coordX = registerConsultDto.getCoordX();
        this.coordY = registerConsultDto.getCoordY();
        this.consultMessage = registerConsultDto.getConsultMessage();
        this.answerState = AnswerState.ROLE_WAIT;
        this.createDate = getCreatedAt();
    }
}
