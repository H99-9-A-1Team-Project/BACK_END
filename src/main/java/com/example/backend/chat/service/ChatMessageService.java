package com.example.backend.chat.service;


import com.example.backend.chat.domain.ChatMessage;
import com.example.backend.chat.domain.ChatRoom;
import com.example.backend.chat.domain.ChatRoomMember;
import com.example.backend.chat.dto.request.MessageRequestDto;
import com.example.backend.chat.dto.response.MessageResponseDto;
import com.example.backend.chat.redis.RedisPublisher;
import com.example.backend.chat.repository.ChatMessageRepository;
import com.example.backend.chat.repository.ChatRoomRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ResponseBodyDto responseBodyDto;
    private final MemberService memberService;
    private final ChatRoomService chatRoomService;
    private final ChatRoomRedisRepository chatRoomRedisRepository;
    private final RedisPublisher redisPublisher;

    public ResponseEntity<?> getMessages(HttpServletRequest request, Long roomNo) {
        List<ChatMessage> messageList = chatMessageRepository.findAllByRoomNo(roomNo);

        List<MessageResponseDto> messageResponseDtoList = new ArrayList<>();

        for (ChatMessage chatMessage : messageList) {
            messageResponseDtoList.add(MessageResponseDto.builder()
                    .messageNo(chatMessage.getMessageNo())
                    .roomNo(chatMessage.getRoomNo())
                    .type(chatMessage.getType())
                    .sender(chatMessage.getSender())
                    .message(chatMessage.getMessage())
                    .date(chatMessage.getDate())
                    .build()
            );
        }

        return responseBodyDto.success(messageResponseDtoList, "메세지 조회 완료");
    }

    @Transactional
    public void sendMessage(MessageRequestDto requestDto) {
        if (requestDto.getType().equals("ENTER")) {
            Member sender = memberService.checkMemberByNick(requestDto.getSender());
            ChatRoom chatRoom = chatRoomService.getChatRoomByRoomNo(requestDto.getRoomNo());
            ChatRoomMember chatRoomMember = chatRoomService.getChatRoomByMemberAndChatRoom(sender, chatRoom);

            if (!chatRoomMember.getEnterStatus()) {
                String roomNo = String.valueOf(chatRoom.getRoomNo());
                chatRoomRedisRepository.enterChatRoom(roomNo);

                LocalDateTime now = LocalDateTime.now();
                String date = now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 E요일 a hh:mm:ss", Locale.KOREA));

                ChatMessage chatMessage = ChatMessage.builder()
                        .roomNo(requestDto.getRoomNo())
                        .type(requestDto.getType())
                        .sender(requestDto.getSender())
                        .message(requestDto.getSender() + "님이 입장하였습니다 :)")
                        .date(date)
                        .build();

                chatMessageRepository.save(chatMessage);

                chatRoomMember.updateEnterStatus();

                MessageResponseDto responseDto = MessageResponseDto.builder()
                        .messageNo(chatMessage.getMessageNo())
                        .roomNo(chatMessage.getRoomNo())
                        .type(chatMessage.getType())
                        .sender(chatMessage.getSender())
                        .message(chatMessage.getMessage())
                        .date(chatMessage.getDate())
                        .build();

                redisPublisher.publish(chatRoomRedisRepository.getTopic(roomNo), responseDto);
            }
        } else if (requestDto.getType().equals("TALK")) {
            LocalDateTime now = LocalDateTime.now();
            String date = now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 E요일 a hh:mm:ss", Locale.KOREA));

            ChatMessage chatMessage = ChatMessage.builder()
                    .roomNo(requestDto.getRoomNo())
                    .type(requestDto.getType())
                    .sender(requestDto.getSender())
                    .message(requestDto.getMessage())
                    .date(date)
                    .build();

            chatMessageRepository.save(chatMessage);

            MessageResponseDto responseDto = MessageResponseDto.builder()
                    .messageNo(chatMessage.getMessageNo())
                    .roomNo(chatMessage.getRoomNo())
                    .type(chatMessage.getType())
                    .sender(chatMessage.getSender())
                    .message(chatMessage.getMessage())
                    .date(chatMessage.getDate())
                    .build();

            String roomNo = String.valueOf(responseDto.getRoomNo());
            redisPublisher.publish(chatRoomRedisRepository.getTopic(roomNo), responseDto);
        }
    }
}