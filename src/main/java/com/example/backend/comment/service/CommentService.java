package com.example.backend.comment.service;

import com.example.backend.chat.domain.ChatRoom;
import com.example.backend.chat.service.ChatRoomService;
import com.example.backend.comment.dto.ImageResponseDto;
import com.example.backend.comment.dto.ConsultMessageRequestDto;
import com.example.backend.comment.repository.CommentRepository;
import com.example.backend.consult.repository.ConsultRepository;
import com.example.backend.global.infra.S3.dto.AwsS3;
import com.example.backend.global.infra.S3.service.AmazonS3Service;
import com.example.backend.global.security.auth.UserDetailsImpl;
import com.example.backend.user.model.Authority;
import com.example.backend.comment.model.Comment;
import com.example.backend.consult.model.Consult;
import com.example.backend.user.model.Realtor;
import com.example.backend.global.exception.customexception.AccessDeniedException;
import com.example.backend.global.exception.customexception.ImageNotFoundException;
import com.example.backend.consult.exception.ConsultNotFoundException;
import com.example.backend.user.exception.user.UserUnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final ConsultRepository consultRepository;
    private final CommentRepository commentRepository;
    private final AmazonS3Service amazonS3Service;
    private final ChatRoomService chatRoomService;

    @Transactional
    public ImageResponseDto registerConsultCommentImg(UserDetailsImpl userDetails, MultipartFile multipartFile) throws IOException {
        validRealtor(userDetails);

        if(multipartFile == null || multipartFile.isEmpty()){
            throw new ImageNotFoundException();
        }

        String imageUrl = amazonS3Service.upload(multipartFile, "CommentAnswerPhotos", userDetails.getUser().getEmail());
        return new ImageResponseDto(imageUrl);
    }

    @Transactional
    public void registerConsultCommentMessage(UserDetailsImpl userDetails, Long consultId, ConsultMessageRequestDto dto) {
        validRealtor(userDetails);
        Consult consult = consultRepository.findById(consultId).orElseThrow(ConsultNotFoundException::new);
        consult.updateState();
//        ChatRoom chatRoom = chatRoomService.createGroupChatRoom(userDetails.getUser(), consult.getTitle());
        Comment comment = Comment.builder()
                .content(dto.getAnswerMessage())
                .realtor((Realtor) userDetails.getUser())
                .consult(consult)
//                .roomNo(chatRoom.getRoomNo())
                .build();

        commentRepository.save(comment);
    }

    public void validAuth(UserDetailsImpl userDetails){
        if(userDetails == null) throw new UserUnauthorizedException();
    }

    private void validRealtor(UserDetailsImpl userDetails){
        validAuth(userDetails);
        if(userDetails.getAuthority() != Authority.ROLE_REALTOR)
            throw new AccessDeniedException();
    }
}
