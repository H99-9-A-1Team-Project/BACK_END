package com.example.backend.comment.repository;

import com.example.backend.comment.model.Comment;
import com.example.backend.user.model.Realtor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {


    List<Comment> findByRealtor(Realtor realtor);

    @Query("select c from Comment c where c.consult.id = :consultId")
    List<Comment> findByConsultId(@Param("consultId") Long consultId);

}
