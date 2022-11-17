package com.example.backend.user.repository;

import com.example.backend.global.entity.Consult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultRepository extends JpaRepository<Consult, Long> {
}
