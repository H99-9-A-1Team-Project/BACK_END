package com.example.backend.comment.repository;

import com.example.backend.global.entity.Comment;
import com.example.backend.global.entity.Realtor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {


    List<Comment> findByRealtor(Realtor realtor);

    @Query("select c from Comment c where c.consult.id = :consultId")
    List<Comment> findAllById(@Param("consultId") Long consultId);

}
