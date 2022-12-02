package com.example.backend.consult.repository;

import com.example.backend.consult.model.Consult;
//import com.example.backend.search.dto.MyConsultResponseDto;
import com.example.backend.search.dto.MyConsultResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConsultRepository extends JpaRepository<Consult, Long>,ConsultRepositoryCustom {
    @Query("select c from Consult c where c.user.id = :userId")
    List<Consult> findAllByUserId(@Param("userId")Long userid);
//    @Query("select c from Consult c where c.user.id = :userId")
//    List<Consult> findAllByUserIdAndKeyWord(@Param("userId")Long userid, @Param("keyword")String keyWord);
    @Query("select c from Consult c where c.answerState = 0")
    List<Consult> findAllByAnswerState(@Param("answerState")int answerstate);

    Consult findByCoordXAndCoordYAndUserId(double x, double y, Long id);

    @Query(value = "SELECT DISTINCT consult.title,\n" +
            " consult.consultMessage,\n" +
            " consult.id,\n" +
            " consult.user_id,\n" +
            " consult.user_id AS cont_user\n" +
            " FROM consultsearch\n" +
            " INNER JOIN consult\n" +
            " ON consult.answerstate = answerstate\n" +
            " WHERE consult.user_id = :user_id AND footsteps_post.user_id = :user_id", nativeQuery = true)
    List<MyConsultResponseDto> findAllByUserIdAndKeywordContaining(@Param("user_id") Long user_id,
                                                                   @Param("keyword") String keyword);
//
////    @Query(value = "SELECT c from Consult c where match(title) against('*keyword*' in boolean mode)")
////    List<MyConsultResponseDto> findAllByUserIdAndKeywordContaining(@Param("user_id") Long user_id,
////                                                                   @Param("keyword") String keyword);
//
//    @Query(value = "SELECT c from Consult c " +
//            "where match(title) against('*keyword*' in boolean mode)", nativeQuery = true)
//    List<MyConsultResponseDto> findAllByUserIdAndKeywordContaining(@Param("user_id") Long user_id,
//                                                                   @Param("keyword") String keyword);
}
