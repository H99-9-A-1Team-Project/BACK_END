package com.example.backend.comment.dto;

import com.example.backend.comment.model.Comment;
import com.example.backend.user.model.Realtor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

//import java.time.format.DateTimeFormatter;

@Builder
@AllArgsConstructor
@Getter
@Data
public class CommentResponseDto {
    private Long Id;
    private String nickname;
    private String answerMessage;

    private String profile;

    private String content;
    private String introMessage;
    private String createdAt;
    private Long likeCount;
    private Long realtorLike;

    private CommentResponseDto toResponseDto(Comment comment){
        return CommentResponseDto.builder()
                .nickname(comment.getRealtor().getNickname())
                .profile(comment.getRealtor().getProfile())
                .introMessage(comment.getRealtor().getIntroMessage())
                .createdAt(comment.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                .answerMessage(comment.getContent())
                .build();
    }

}
