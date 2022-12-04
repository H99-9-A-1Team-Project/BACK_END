package com.example.backend.chat.dto.request;

import lombok.Getter;

@Getter
public class ChatRoomRequestDto {
    private Long memberNo;
    private Long roomNo;
    private String title;
}