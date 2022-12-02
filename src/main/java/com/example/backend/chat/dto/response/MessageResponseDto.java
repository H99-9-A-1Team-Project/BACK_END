package com.example.backend.chat.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MessageResponseDto {
    private Long messageNo;
    private Long roomNo;
    private String type;
    private String sender;
    private String message;
    private String date;
    
    
    public MessageResponseDto() {
        
    }
    
    @Builder
    public MessageResponseDto(Long messageNo, Long roomNo, String type,
                              String sender, String message, String date) {
        this.messageNo = messageNo;
        this.roomNo = roomNo;
        this.type = type;
        this.sender = sender;
        this.message = message;
        this.date = date;
    }
}