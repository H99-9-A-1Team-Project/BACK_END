package com.example.backend.footsteps.repository;

import com.example.backend.footsteps.model.FootstepsPost;
import com.example.backend.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FootstepsRepository extends JpaRepository<FootstepsPost,Long> {
    List<FootstepsPost> findByUser(User user);

    List<FootstepsPost> findByUserId(Long id);

    @Query(value = "SELECT DISTINCT footsteps_post.accessibility, \n" +
            "\t\t\tfootsteps_post.cctv, \n" +
            " footsteps_post.coordfx,\n" +
            " footsteps_post.coordfy,\n" +
            " footsteps_post.create_date,\n" +
            " footsteps_post.destroy,\n" +
            " footsteps_post.draft,\n" +
            " footsteps_post.drain,\n" +
            " footsteps_post.extra_memo,\n" +
            " footsteps_post.hill,\n" +
            " footsteps_post.hospital,\n" +
            " footsteps_post.id,\n" +
            " footsteps_post.loan,\n" +
            " footsteps_post.mart,\n" +
            " footsteps_post.mold,\n" +
            " footsteps_post.noise, \n" +
            " footsteps_post.option,\n" +
            " footsteps_post.park,\n" +
            " footsteps_post.price,\n" +
            " footsteps_post.review,\n" +
            " footsteps_post.security_window,\n" +
            " footsteps_post.size,\n" +
            " footsteps_post.sun,\n" +
            " footsteps_post.title,\n" +
            " footsteps_post.uti_room,\n" +
            " footsteps_post.vent,\n" +
            " footsteps_post.ventil,\n" +
            " footsteps_post.water,\n" +
            " footsteps_post.user_id,\n" +
            " consult.user_id AS cont_user\n" +
            " FROM footsteps_post\n" +
            " INNER JOIN consult\n" +
            " ON consult.coordx = coordfx\n" +
            " AND consult.coordy = coordfy\n" +
            " WHERE consult.user_id = :user_id AND footsteps_post.user_id = :user_id", nativeQuery = true)
    List<FootstepsPost> findByUserInfo(@Param("user_id") Long user_id);
}
