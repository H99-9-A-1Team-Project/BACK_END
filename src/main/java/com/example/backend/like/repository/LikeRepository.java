package com.example.backend.like.repository;

import com.example.backend.comment.model.Comment;
import com.example.backend.consult.model.Consult;
import com.example.backend.like.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Like findByUserAndComment(Long id, Comment comment);


}
