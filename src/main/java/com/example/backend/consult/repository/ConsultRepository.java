package com.example.backend.consult.repository;

import com.example.backend.consult.model.Consult;
//import com.example.backend.search.dto.MyConsultResponseDto;
import com.example.backend.search.dto.MyConsultResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ConsultRepository extends JpaRepository<Consult, Long>,ConsultRepositoryCustom {
    @Query("select c from Consult c where c.user.id = :userId")
    List<Consult> findAllByUserId(@Param("userId")Long userid);

    @Query("select c from Consult c where c.answerState = 0")
    List<Consult> findAllByAnswerState(@Param("answerState")int answerstate);

    Consult findByCoordXAndCoordYAndUserId(double x, double y, Long id);

    @Query(value = "SELECT * from consult c " +
            "where title Like '%keyword%' ", nativeQuery = true)
    List<MyConsultResponseDto> findAllByKeywordContaining(@Param("keyword") String keyword);

    Consult findByUserId(Long id);
}
