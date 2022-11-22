package com.example.backend.Comment.service;

import com.example.backend.Comment.dto.ConsultMessageRequestDto;
import com.example.backend.Comment.dto.ImageResponseDto;
import com.example.backend.Comment.repository.CommentRepository;
import com.example.backend.consult.repository.ConsultRepository;
import com.example.backend.global.S3.dto.AwsS3;
import com.example.backend.global.S3.service.AmazonS3Service;
import com.example.backend.global.config.auth.UserDetailsImpl;
import com.example.backend.global.entity.Authority;
import com.example.backend.global.entity.Comment;
import com.example.backend.global.entity.Consult;
import com.example.backend.global.exception.customexception.common.AccessDeniedException;
import com.example.backend.global.exception.customexception.common.ImageNotFoundException;
import com.example.backend.global.exception.customexception.consult.ConsultNotFoundException;
import com.example.backend.global.exception.customexception.user.UserUnauthorizedException;
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
    @Value("${cloud.aws.credentials.domain}")
    private String amazonS3Domain;

    @Transactional
    public ImageResponseDto registerConsultCommentImg(UserDetailsImpl userDetails, MultipartFile multipartFile) throws IOException {
        validRealtor(userDetails);

        if(multipartFile == null || multipartFile.isEmpty()){
            throw new ImageNotFoundException();
        }

        AwsS3 image = amazonS3Service.upload(multipartFile, "CommentAnswerPhotos", userDetails.getUser().getEmail());
        String imgUrl = amazonS3Domain + URLEncoder.encode(image.getKey(), StandardCharsets.US_ASCII);
        return new ImageResponseDto(imgUrl);
    }

    @Transactional
    public void registerConsultCommentMessage(UserDetailsImpl userDetails, Long consultId, ConsultMessageRequestDto dto) {
        validRealtor(userDetails);

        Consult consult = consultRepository.findById(consultId).orElseThrow(ConsultNotFoundException::new);
        consult.updateState();

        Comment comment = Comment.builder()
                .content(dto.getAnswerMessage())
                .user(userDetails.getUser())
                .consult(consult)
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
