package com.example.backend.consult.repository;

import com.example.backend.global.entity.Consult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConsultRepository extends JpaRepository<Consult, Long>,ConsultRepositoryCustom {
    @Query("select c from Consult c where c.user.id = :userId")
    List<Consult> findAllByUserId(@Param("userId")Long userid);
    @Query("select c from Consult c where c.answerState = 0")
    List<Consult> findAllByAnswerState(@Param("answerState")int answerstate);

    @Query(value = "SELECT * FROM Consult WHERE realtor", nativeQuery = true)
    List<Consult> findByRealtorAndAnswerState();
    @Query("select c from Consult c where c.coordX = :coordx")
    Object findAllByCoordX(@Param("coordx")double coordx);
    @Query("select c from Consult c where c.coordY = :coordy")
    Object findAllByCoordY(@Param("coordy")double coordy);

    Consult findAllByUser(Long id);
}
