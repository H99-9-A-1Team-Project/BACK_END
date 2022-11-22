package com.example.backend.Comment.dto;

import com.example.backend.global.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private String imgurl;
    public CommentResponse(Comment comment){
        this.imgurl = comment.getImgurl();
    }
}
