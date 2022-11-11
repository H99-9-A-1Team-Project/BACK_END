package com.example.backend.repository.user;

import com.example.backend.entity.user.Realtor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RealtorRepository extends JpaRepository<Realtor, Long> {
    Optional<Realtor> findByEmail(String email);
}
