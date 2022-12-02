package com.example.backend.chat.service;

import com.example.backend.chat.domain.ChatMessage;
import com.example.backend.chat.domain.ChatRoom;
import com.example.backend.chat.domain.ChatRoomMember;
import com.example.backend.chat.dto.request.ChatRoomRequestDto;
import com.example.backend.chat.dto.response.ChatRoomResponseDto;
import com.example.backend.chat.repository.ChatMessageRepository;
import com.example.backend.chat.repository.ChatRoomMemberRepository;
import com.example.backend.chat.repository.ChatRoomRedisRepository;
import com.example.backend.chat.repository.ChatRoomRepository;
import com.example.backend.global.config.jwt.JWTUtil;
import com.example.backend.global.exception.CustomException;
import com.example.backend.global.exception.customexception.NotFoundException;
import com.example.backend.global.security.auth.UserDetailsImpl;
import com.example.backend.user.dto.response.MemberResponseDto;
import com.example.backend.user.exception.user.UserUnauthorizedException;
import com.example.backend.user.model.Member;
import com.example.backend.user.model.User;
import com.example.backend.user.service.UserService;
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
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final UserService memberService;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomRedisRepository chatRoomRedisRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final ChatMessageRepository chatMessageRepository;


    @Transactional
    public ChatRoomResponseDto createPersonalChatRoom(HttpServletRequest request,
                                                    UserDetailsImpl userDetails,
                                                    ChatRoomRequestDto requestDto) {
       validAuth(userDetails);
        User creator = userDetails.getUser();
        User target = memberService.checkMemberByMemberNo(requestDto.getMemberNo());


        ChatRoom chatRoom = checkPersonalChatRoomByMembers(creator, target);

        chatRoomRedisRepository.createChatRoom(chatRoom);

        LocalDateTime now = LocalDateTime.now();
        String date = now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 E요일 a hh:mm:ss", Locale.KOREA));

        ChatMessage chatMessage = ChatMessage.builder()
                .roomNo(chatRoom.getRoomNo())
                .type("SYSTEM")
                .sender("SYSTEM")
                .message("대화가 시작되었습니다 :)")
                .date(date)
                .build();

        chatMessageRepository.save(chatMessage);

        return ChatRoomResponseDto.builder()
                        .roomNo(chatRoom.getRoomNo())
                        .build();
    }

    @Transactional
    public ChatRoom createGroupChatRoom(User user, String title) {
        // chatRoom 생성
        ChatRoom chatRoom = ChatRoom.builder()
                .type("group")
                .title(title)
                .build();

        chatRoomRepository.save(chatRoom);

        chatRoomRedisRepository.createChatRoom(chatRoom);

        LocalDateTime now = LocalDateTime.now();
        String date = now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 E요일 a hh:mm:ss", Locale.KOREA));

        ChatMessage chatMessage = ChatMessage.builder()
                .roomNo(chatRoom.getRoomNo())
                .type("SYSTEM")
                .sender("SYSTEM")
                .message("대화가 시작되었습니다 :)")
                .date(date)
                .build();

        chatMessageRepository.save(chatMessage);

        ChatRoomMember chatRoomMember = ChatRoomMember.builder()
                .chatRoom(chatRoom)
                .user(user)
                .enterStatus(false)
                .build();

        chatRoomMemberRepository.save(chatRoomMember);

        return chatRoom;
    }

    @Transactional
    public ChatRoomMember createGroupChatRoomMember(HttpServletRequest request,
                                                    UserDetailsImpl userDetails,
                                                    ChatRoomRequestDto requestDto) {
        User user = userDetails.getUser();
        ChatRoom chatRoom = getChatRoomByRoomNo(requestDto.getRoomNo());

        ChatRoomMember chatRoomMember = checkChatRoomMemberByMemberAndChatRoom(user, chatRoom);

        return chatRoomMember;

    }

    @Transactional(readOnly = true)
    public List<ChatRoomResponseDto> getChatRooms(HttpServletRequest request, UserDetailsImpl userDetails) {

        List<ChatRoom> chatRoomList = chatRoomRepository.findByAllChatRoom(userDetails.getUser());
        List<ChatRoomResponseDto> chatRoomResponseDtoList = new ArrayList<>();

        for (ChatRoom chatRoom : chatRoomList) {
            List<ChatRoomMember> chatRoomMemberList = chatRoomMemberRepository.findByChatRoom(chatRoom);
            List<MemberResponseDto> memberResponseDtoList = new ArrayList<>();

            for (ChatRoomMember chatRoomMember : chatRoomMemberList) {
                memberResponseDtoList.add(
                        MemberResponseDto.builder()
                                .memberNo(chatRoomMember.getUser().getId())
                                .nick(chatRoomMember.getUser().getNickname())
                                .build()
                );
            }

            ChatMessage chatMessage = chatMessageRepository.findTop1ByRoomNoOrderByMessageNoDesc(chatRoom.getRoomNo()).orElseThrow(
                    NotFoundException::new
            );

            chatRoomResponseDtoList.add(
                    ChatRoomResponseDto.builder()
                            .roomNo(chatRoom.getRoomNo())
                            .type(chatRoom.getType())
                            .title(chatRoom.getTitle())
                            .chatRoomMembers(memberResponseDtoList)
                            .lastDate(chatMessage.getDate())
                            .lastMessage(chatMessage.getMessage())
                            .build()
            );
        }

        return chatRoomResponseDtoList;
    }


    @Transactional
    public ChatRoom checkPersonalChatRoomByMembers(User creator, User target) {
        return chatRoomRepository.findByChatRoom(creator, target).orElseGet(
                () -> {
                    ChatRoom chatRoom = ChatRoom.builder()
                            .roomKey(UUID.randomUUID().toString())
                            .type("personal")
                            .title("1:1")
                            .build();

                    chatRoomRepository.save(chatRoom);

                    ChatRoomMember creatorRoomMember = ChatRoomMember.builder()
                            .chatRoom(chatRoom)
                            .user(creator)
                            .enterStatus(false)
                            .build();

                    chatRoomMemberRepository.save(creatorRoomMember);

                    ChatRoomMember targetRoomMember = ChatRoomMember.builder()
                            .chatRoom(chatRoom)
                            .user(target)
                            .enterStatus(false)
                            .build();

                    chatRoomMemberRepository.save(targetRoomMember);

                    return chatRoom;
                }
        );
    }


    @Transactional
    public ChatRoomMember checkChatRoomMemberByMemberAndChatRoom(User user, ChatRoom chatRoom) {
        return chatRoomMemberRepository.findByuserAndChatRoom(user, chatRoom).orElseGet(
                () -> {
                    ChatRoomMember chatRoomMember = ChatRoomMember.builder()
                            .chatRoom(chatRoom)
                            .user(user)
                            .enterStatus(false)
                            .build();

                    chatRoomMemberRepository.save(chatRoomMember);

                    return chatRoomMember;
                }
        );
    }

    @Transactional(readOnly = true)
    public ChatRoom getChatRoomByRoomNo(Long roomNo) {
        return chatRoomRepository.findById(roomNo).orElseThrow(
                NotFoundException::new
        );
    }

    @Transactional(readOnly = true)
    public ChatRoomMember getChatRoomByMemberAndChatRoom(User user, ChatRoom chatRoom) {
        return chatRoomMemberRepository.findByuserAndChatRoom(user, chatRoom).orElseThrow(
                NotFoundException::new
        );
    }
    public void validAuth(UserDetailsImpl userDetails){
        if(userDetails == null) throw new UserUnauthorizedException();
    }
}