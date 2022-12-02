package com.example.backend.chat.repository;

import com.example.backend.chat.domain.ChatRoom;
import com.sparta.daengtionary.category.chat.domain.ChatRoom;
import com.sparta.daengtionary.category.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    @Query(value =
            "SELECT cr.* " +
            "FROM chat_room cr " +
            "JOIN chat_room_member crm1 ON cr.room_no = crm1.room_no " +
            "JOIN chat_room_member crm2 ON cr.room_no = crm2.room_no " +
            "WHERE cr.type = 'personal' " +
            "AND crm1.member_no = :creator " +
            "AND crm2.member_no = :target",
            nativeQuery = true)
    Optional<ChatRoom> findByChatRoom(@Param("creator") Member creator, @Param("target") Member target);

    @Query(value =
            "SELECT cr.* " +
            "FROM chat_room cr " +
            "JOIN chat_room_member crm " +
            "ON cr.room_no = crm.room_no " +
            "WHERE crm.member_no = :member",
            nativeQuery = true)
    List<ChatRoom> findByAllChatRoom(@Param("member") Member member);
}