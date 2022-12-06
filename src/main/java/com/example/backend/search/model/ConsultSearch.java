package com.example.backend.search.model;

import com.example.backend.comment.model.Comment;
import com.example.backend.consult.model.AnswerState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder
@Entity
public class ConsultSearch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String keyword;

    @Column
    private String consultMessage;

    @Column
    private AnswerState answerState;

    @DateTimeFormat
    private LocalDateTime createDate;

    @Column
    private String searchWord;

}
