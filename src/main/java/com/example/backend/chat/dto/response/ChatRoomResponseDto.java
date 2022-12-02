package com.example.backend.chat.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.daengtionary.category.member.dto.response.MemberResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatRoomResponseDto {
    private Long roomNo;
    private String type;
    private String title;
    private List<MemberResponseDto> chatRoomMembers;
    private String lastDate;
    private String lastMessage;


    @Builder
    public ChatRoomResponseDto(Long roomNo, String type, String title, List<MemberResponseDto> chatRoomMembers,
                               String lastDate, String lastMessage) {
        this.roomNo = roomNo;
        this.type = type;
        this.title = title;
        this.chatRoomMembers = chatRoomMembers;
        this.lastDate = lastDate;
        this.lastMessage = lastMessage;
    }
}