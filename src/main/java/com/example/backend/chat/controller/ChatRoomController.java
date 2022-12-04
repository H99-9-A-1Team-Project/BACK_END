package com.example.backend.chat.controller;


import com.example.backend.chat.dto.request.ChatRoomRequestDto;
import com.example.backend.chat.service.ChatRoomService;
import com.example.backend.global.security.auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    @PostMapping("/room/personal")
    public ResponseEntity<?> createPersonalChatRoom(HttpServletRequest request,
                                                    @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                    @RequestBody ChatRoomRequestDto requestDto) {
        return ResponseEntity.ok(chatRoomService.createPersonalChatRoom(request,userDetails, requestDto));
    }
    @PostMapping("/room/into")
    public ResponseEntity<?> createGroupChatRoomMember(HttpServletRequest request,
                                                       @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                       @RequestBody ChatRoomRequestDto requestDto) {
        return ResponseEntity.ok(chatRoomService.createGroupChatRoomMember(request,userDetails, requestDto));
    }

    @GetMapping("/rooms")
    public ResponseEntity<?> getChatRooms(HttpServletRequest request,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(chatRoomService.getChatRooms(request,userDetails));
    }
}