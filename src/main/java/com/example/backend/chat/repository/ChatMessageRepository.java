package com.example.backend.chat.repository;

import com.example.backend.chat.domain.ChatMessage;
\import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findAllByRoomNo(Long roomNo);
    Boolean existsByRoomNo(long roomNo);
    Optional<ChatMessage> findTop1ByRoomNoOrderByMessageNoDesc(Long roomNo);
}