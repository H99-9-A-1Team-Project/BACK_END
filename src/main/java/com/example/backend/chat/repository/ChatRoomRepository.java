package com.example.backend.chat.repository;

import com.example.backend.chat.domain.ChatRoom;
import com.example.backend.user.model.Member;
import com.example.backend.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
//    @Query(value =
//            "SELECT cr.* " +
//            "FROM chat_room cr " +
//            "JOIN chat_room_member crm " +
//            "ON cr.room_no = crm.room_no " +
//            "WHERE crm.member_no = :member",
//            nativeQuery = true)
//    List<ChatRoom> findByAllChatRoom(@Param("member") User member);
}