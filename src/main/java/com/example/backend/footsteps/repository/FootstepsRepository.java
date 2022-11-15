package com.example.backend.footsteps.repository;

import com.example.backend.global.entity.FootstepsPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FootstepsRepository extends JpaRepository<FootstepsPost,Long> {
}
