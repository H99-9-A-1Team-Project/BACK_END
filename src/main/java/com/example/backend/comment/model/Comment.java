package com.example.backend.comment.model;

import com.example.backend.consult.model.Consult;
import com.example.backend.user.model.Realtor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicInsert
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "realtor_id",nullable = false)
    private Realtor realtor;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consult_id", nullable = false)
    private Consult consult;

    @DateTimeFormat
    private LocalDateTime createdAt; // 날짜
    @Column
    private Long likeCount;
    @Column
    private Long roomNo;

    @PrePersist // DB에 INSERT 되기 직전에 실행. 즉 DB에 값을 넣으면 자동으로 실행됨
    public void createDate() {
        this.createdAt = LocalDateTime.now();
        this.likeCount = 0L;
    }
    public void update(Long likeCount){
        this.likeCount = likeCount;
    }
}
