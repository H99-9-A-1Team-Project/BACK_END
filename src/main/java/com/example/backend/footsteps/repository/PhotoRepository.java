package com.example.backend.footsteps.repository;

import com.example.backend.footsteps.model.FootstepsPost;
import com.example.backend.footsteps.model.Photo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo,Long> {
    Page<Photo> findAllByFootstepsPost(FootstepsPost post, Pageable pageable);
}
