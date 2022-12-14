package com.example.backend.user.repository;

import com.example.backend.user.model.Realtor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RealtorRepository extends JpaRepository<Realtor, Long> {
    Optional<Realtor> findByEmail(String email);

    List<Realtor> findByAccountCheck(Long i);
}
