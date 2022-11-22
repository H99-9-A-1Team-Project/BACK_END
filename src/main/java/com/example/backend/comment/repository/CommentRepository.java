package com.example.backend.comment.repository;

import com.example.backend.global.entity.Comment;
import com.example.backend.global.entity.Realtor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByRealtor(Realtor realtor);
}
