package com.example.backend.repository.user;

import com.example.backend.entity.user.Realtor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RealtorRepository extends JpaRepository<Realtor, Integer> {
}
