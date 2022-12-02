package com.example.backend.chat.service;

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

import static com.sparta.daengtionary.aop.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final TokenProvider tokenProvider;
    private final MemberService memberService;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomRedisRepository chatRoomRedisRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ResponseBodyDto responseBodyDto;


    @Transactional
    public ResponseEntity<?> createPersonalChatRoom(HttpServletRequest request,
                                                    ChatRoomRequestDto requestDto) {
        Member creator = tokenProvider.getMemberFromAuthentication();

        Member target = memberService.checkMemberByMemberNo(requestDto.getMemberNo());

        if (creator.equals(target)) {
            throw new CustomException(CANNOT_CHAT_WITH_ME);
        }

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

        return responseBodyDto.success(ChatRoomResponseDto.builder()
                        .roomNo(chatRoom.getRoomNo())
                        .build(),
                "채팅방 준비 완료");
    }

    @Transactional
    public ChatRoom createGroupChatRoom(Member member, String title) {
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
                .member(member)
                .enterStatus(false)
                .build();

        chatRoomMemberRepository.save(chatRoomMember);

        return chatRoom;
    }

    @Transactional
    public ResponseEntity<?> createGroupChatRoomMember(HttpServletRequest request,
                                                       ChatRoomRequestDto requestDto) {
        Member member = tokenProvider.getMemberFromAuthentication();

        ChatRoom chatRoom = getChatRoomByRoomNo(requestDto.getRoomNo());

        ChatRoomMember chatRoomMember = checkChatRoomMemberByMemberAndChatRoom(member, chatRoom);

        return responseBodyDto.success("참여가 완료되었습니다 :)");
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getChatRooms(HttpServletRequest request) {
        Member member = tokenProvider.getMemberFromAuthentication();

        List<ChatRoom> chatRoomList = chatRoomRepository.findByAllChatRoom(member);
        List<ChatRoomResponseDto> chatRoomResponseDtoList = new ArrayList<>();

        for (ChatRoom chatRoom : chatRoomList) {
            List<ChatRoomMember> chatRoomMemberList = chatRoomMemberRepository.findByChatRoom(chatRoom);
            List<MemberResponseDto> memberResponseDtoList = new ArrayList<>();

            for (ChatRoomMember chatRoomMember : chatRoomMemberList) {
                memberResponseDtoList.add(
                        MemberResponseDto.builder()
                                .memberNo(chatRoomMember.getMember().getMemberNo())
                                .nick(chatRoomMember.getMember().getNick())
                                .build()
                );
            }

            ChatMessage chatMessage = chatMessageRepository.findTop1ByRoomNoOrderByMessageNoDesc(chatRoom.getRoomNo()).orElseThrow(
                    () -> new CustomException(NOT_FOUND_CHAT_ROOM)
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

        return responseBodyDto.success(chatRoomResponseDtoList, "채팅방 조회 완료");
    }


    @Transactional
    public ChatRoom checkPersonalChatRoomByMembers(Member creator, Member target) {
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
                            .member(creator)
                            .enterStatus(false)
                            .build();

                    chatRoomMemberRepository.save(creatorRoomMember);

                    ChatRoomMember targetRoomMember = ChatRoomMember.builder()
                            .chatRoom(chatRoom)
                            .member(target)
                            .enterStatus(false)
                            .build();

                    chatRoomMemberRepository.save(targetRoomMember);

                    return chatRoom;
                }
        );
    }

    @Transactional
    public ChatRoomMember checkChatRoomMemberByMemberAndChatRoom(Member member, ChatRoom chatRoom) {
        return chatRoomMemberRepository.findByMemberAndChatRoom(member, chatRoom).orElseGet(
                () -> {
                    ChatRoomMember chatRoomMember = ChatRoomMember.builder()
                            .chatRoom(chatRoom)
                            .member(member)
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
                () -> new CustomException(NOT_FOUND_CHAT_ROOM)
        );
    }

    @Transactional(readOnly = true)
    public ChatRoomMember getChatRoomByMemberAndChatRoom(Member member, ChatRoom chatRoom) {
        return chatRoomMemberRepository.findByMemberAndChatRoom(member, chatRoom).orElseThrow(
                () -> new CustomException(NOT_FOUND_CHAT_ROOM_MEMBER)
        );
    }
}