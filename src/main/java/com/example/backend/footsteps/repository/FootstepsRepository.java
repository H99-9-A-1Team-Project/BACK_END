package com.example.backend.footsteps.repository;

import com.example.backend.global.entity.FootstepsPost;
import com.example.backend.global.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FootstepsRepository extends JpaRepository<FootstepsPost,Long> {
    List<FootstepsPost> findByUser(User user);

    @Query(value = "SELECT footsteps_post.accessibility AS accessibility, \n" +
            "footsteps_post.cctv, \n" +
            "footsteps_post.coordfx, \n" +
            "footsteps_post.coordfy, \n" +
            "footsteps_post.create_date, \n" +
            "footsteps_post.destroy, \n" +
            "footsteps_post.draft, \n" +
            "footsteps_post.drain, \n" +
            "footsteps_post.extra_memo, \n" +
            "footsteps_post.hill, \n" +
            "footsteps_post.hospital, \n" +
            "footsteps_post.id, \n" +
            "footsteps_post.loan, \n" +
            "footsteps_post.mart, \n" +
            "footsteps_post.mold, \n" +
            "footsteps_post.noise, \n" +
            "footsteps_post.option,\n" +
            "footsteps_post.park,\n" +
            "footsteps_post.price,\n" +
            "footsteps_post.review,\n" +
            "footsteps_post.security_window,\n" +
            "footsteps_post.size,\n" +
            "footsteps_post.sun,\n" +
            "footsteps_post.title,\n" +
            "footsteps_post.uti_room,\n" +
            "footsteps_post.vent,\n" +
            "footsteps_post.ventil,\n" +
            "footsteps_post.water\n" +
            "\tFROM footsteps_post \n" +
            "\tINNER JOIN consult \n" +
            "\tON consult.coordx = coordfx \n" +
            "\tAND consult.coordy = coordfy\n" +
            "    WHERE consult.user_id = :user_id", nativeQuery = true)
    List<FootstepsPost> findByUserInfo(@Param("user_id") Long user_id);

}
