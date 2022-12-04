package com.example.backend.chat.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageRequestDto {
    private Long roomNo;
    private String type;
    private String sender;
    private String message;
}