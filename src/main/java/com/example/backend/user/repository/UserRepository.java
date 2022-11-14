package com.example.backend.user.repository;


import com.example.backend.global.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmailAndPassword(String email, String password);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
