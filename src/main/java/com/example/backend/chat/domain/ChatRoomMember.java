package com.example.backend.chat.domain;


import com.example.backend.user.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor
public class ChatRoomMember implements Serializable {
    private static final long serialVersionUID = 6494678977089006639L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomMemberNo;

    @JoinColumn(name = "roomNo", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ChatRoom chatRoom;

    @JoinColumn(name = "memberNo", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false)
    private Boolean enterStatus;


    @Builder
    public ChatRoomMember(Long roomMemberNo, ChatRoom chatRoom, User user, Boolean enterStatus) {
        this.roomMemberNo = roomMemberNo;
        this.chatRoom = chatRoom;
        this.user = user;
        this.enterStatus = enterStatus;
    }

    public void updateEnterStatus() {
        this.enterStatus = true;
    }
}