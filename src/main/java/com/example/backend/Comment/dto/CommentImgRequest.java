package com.example.backend.Comment.dto;

import com.example.backend.global.entity.Comment;
import com.example.backend.global.entity.Consult;
import com.example.backend.global.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentImgRequest {
    private String imgurl;

    public Comment toEntity(User user, Consult consult){
        return new Comment(user, consult, this.imgurl);
    }

}
