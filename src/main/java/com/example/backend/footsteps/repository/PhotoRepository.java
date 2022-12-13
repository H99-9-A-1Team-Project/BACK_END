package com.example.backend.footsteps.repository;

import com.example.backend.footsteps.model.FootstepsPost;
import com.example.backend.footsteps.model.Photo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo,Long> {
    List<Photo> findByFootstepsPost(FootstepsPost post);

    Page<Photo> findAllByFootstepsPost(FootstepsPost post, Pageable pageable);
}
