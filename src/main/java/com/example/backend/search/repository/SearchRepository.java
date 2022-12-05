package com.example.backend.search.repository;

import com.example.backend.consult.model.Consult;
import com.example.backend.search.dto.MyConsultResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SearchRepository extends JpaRepository<Consult, Long> {
    @Query(value = "SELECT * from consult c " +
            "where title Like '%keyword%' ", nativeQuery = true)
    List<MyConsultResponseDto> findAllByKeyword(@Param("keyword") String keyword);
}
