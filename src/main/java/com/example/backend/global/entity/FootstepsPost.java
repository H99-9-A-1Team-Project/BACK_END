package com.example.backend.global.entity;

import com.example.backend.global.config.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
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

    //제목
    @Column(nullable = false)
    private String title;

    //상답 답변 유무
    @Column(nullable = false)
    @ColumnDefault("ROLE_WAIT")
    @Enumerated(EnumType.STRING)
    private AnswerState answerState;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private double size;

    @Column(nullable = false)
    private String review;


    @Column
    private boolean elevator;

    @Column
    private String sun;

    @Column
    private String mold;

    @Column
    private String water;

    @Column
    private String drain;

    @Column
    private String option;

    @Column
    private String ventil;

    @Column
    private String destroy;

    @Column
    private String owenr;

    @Column
    private String security;

}
