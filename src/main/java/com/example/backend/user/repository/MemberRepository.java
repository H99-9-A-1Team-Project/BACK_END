package com.example.backend.user.repository;

import com.example.backend.user.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email);
}
