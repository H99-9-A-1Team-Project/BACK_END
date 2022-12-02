package com.example.backend.chat.model;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
public class ChatRoom  {
    private static final long serialVersionUID = 6494678977089006639L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomNo;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "chatRoom")
    private List<ChatRoomMember> chatRoomMembers;


    public ChatRoom() {

    }

    @Builder
    public ChatRoom(Long roomNo, String roomKey, String type, String title) {
        this.roomNo = roomNo;
        this.type = type;
        this.title = title;
    }
}