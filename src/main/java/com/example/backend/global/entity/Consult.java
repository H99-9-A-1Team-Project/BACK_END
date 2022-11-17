package com.example.backend.global.entity;

import com.example.backend.consult.dto.RegisterConsultDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Consult {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column//(name="post_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Double coordX;

    @Column(nullable = false)
    private Double coordY;

    @Column(nullable = false)
    private int check1;

    @Column(nullable = false)
    private int check2;

    @Column(nullable = false)
    private int check3;

    @Column(nullable = false)
    private int check4;

    @Column(nullable = false)
    private int check5;

    @Column(nullable = false)
    private int check6;

    @Column(nullable = false)
    private String consultMessage;

    @Column(nullable = false)
//    @Enumerated(EnumType.STRING)
    private AnswerState answerState;

    public Consult(RegisterConsultDto registerConsultDto) {
        this.title = registerConsultDto.getTitle();
        this.coordX = registerConsultDto.getCoordX();
        this.coordY = registerConsultDto.getCoordY();
        this.consultMessage = registerConsultDto.getConsultMessage();
        this.answerState = AnswerState.ROLE_WAIT;
    }
}
