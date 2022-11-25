package com.example.backend.consult.model;

import com.example.backend.global.security.auth.UserDetailsImpl;
import com.example.backend.comment.model.Comment;
import com.example.backend.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Consult {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @OneToMany(mappedBy = "consult")
    private List<Comment> commentList = new ArrayList<>();

    @Column
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

    @Column
    private String consultMessage;

    @Column
//    @Enumerated(EnumType.STRING)
    private AnswerState answerState;
    @DateTimeFormat
    private LocalDateTime createDate; // 날짜

    @PrePersist // DB에 INSERT 되기 직전에 실행. 즉 DB에 값을 넣으면 자동으로 실행됨
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

    public void updateState() {
        this.answerState = AnswerState.ANSWER;
    }
    public void updateState2(AnswerState answerState) {
        this.answerState = AnswerState.FINISH;
    }

    public boolean checkOwnerByUserId(UserDetailsImpl userDetails) {
        return this.user.getEmail().equals(userDetails.getUser().getEmail());
    }
}
