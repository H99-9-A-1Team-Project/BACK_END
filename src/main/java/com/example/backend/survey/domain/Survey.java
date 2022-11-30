package com.example.backend.survey.domain;

import com.example.backend.consult.model.AnswerState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Survey {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private boolean check1;

    @Column(nullable = false)
    private boolean check2;

    @Column(nullable = false)
    private boolean check3;

    @Column
    @Lob
    private String surveyMessage;

    @DateTimeFormat
    private LocalDateTime createDate; // 날짜

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

}