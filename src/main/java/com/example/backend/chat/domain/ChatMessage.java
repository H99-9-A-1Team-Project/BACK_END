package com.example.backend.chat.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
public class ChatMessage implements Serializable {
    private static final long serialVersionUID = 6494678977089006639L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageNo;

    @Column
    private Long roomNo;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String sender;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private String date;


    public ChatMessage() {

    }

    @Builder
    public ChatMessage(Long messageNo, Long roomNo, String roomKey, String type,
                       String message, String sender, String date) {
        this.messageNo = messageNo;
        this.roomNo = roomNo;
        this.type = type;
        this.sender = sender;
        this.message = message;
        this.date = date;
    }
}