package com.example.backend.chat.repository;

import com.example.backend.chat.domain.ChatRoom;
import com.example.backend.chat.domain.ChatRoomMember;
import com.example.backend.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {
    List<ChatRoomMember> findByChatRoom(ChatRoom chatRoom);
    Optional<ChatRoomMember> findByuserAndChatRoom(User user, ChatRoom chatRoom);
}