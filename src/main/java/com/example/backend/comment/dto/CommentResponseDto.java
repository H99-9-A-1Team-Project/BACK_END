package com.example.backend.comment.dto;

import com.example.backend.comment.model.Comment;
import com.example.backend.user.model.Realtor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Builder
@AllArgsConstructor
@Getter
@Data
public class CommentResponseDto {
    private String nickname;
    private String answerMessage;

    private String profile;

    private String content;
    private String introMessage;
    private String createdAt;

    public CommentResponseDto(Realtor realtor, Comment comment){
        this.profile = realtor.getProfile();
        this.introMessage = realtor.getIntroMessage();
        this.content = comment.getContent();
        this.nickname = comment.getRealtor().getNickname();
        this.createdAt = comment.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }
}
