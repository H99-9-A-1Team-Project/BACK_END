package com.example.backend.footsteps.repository;

import com.example.backend.global.entity.FootstepsPost;
import com.example.backend.global.entity.Photo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo,Long> {
    Page<Photo> findAllByFootstepsPost(FootstepsPost post, Pageable pageable);
}
