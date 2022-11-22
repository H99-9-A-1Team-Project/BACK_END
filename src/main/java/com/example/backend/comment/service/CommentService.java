package com.example.backend.Comment.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.example.backend.Comment.dto.CommentResponse;
import com.example.backend.Comment.repository.CommentRepository;
import com.example.backend.consult.repository.ConsultRepository;
import com.example.backend.global.config.S3.CommonUtils;
import com.example.backend.global.config.auth.UserDetailsImpl;
import com.example.backend.global.entity.Authority;
import com.example.backend.global.entity.Comment;
import com.example.backend.global.entity.Consult;
import com.example.backend.global.exception.customexception.common.AccessDeniedException;
import com.example.backend.global.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {
    private final AmazonS3Client amazonS3Client;
    private final CommentRepository commentrepository;
    private final ConsultRepository consultRepository;
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    public Response createCommentImg(MultipartFile multipartFile, Long consultId, UserDetailsImpl userDetails)  throws IOException {
        validRealtor(userDetails);
        Consult consult = consultRepository.findById(consultId).orElseThrow(  //Null이면 던저라
                () -> new IllegalArgumentException("해당 아이디를 가진 게시글이 존재하지 않습니다.")
        );
        String imgurl = null;

        if (!multipartFile.isEmpty()) {
            String fileName = CommonUtils.buildFileName(multipartFile.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());

            byte[] bytes = IOUtils.toByteArray(multipartFile.getInputStream());
            objectMetadata.setContentLength(bytes.length);
            ByteArrayInputStream byteArrayIs = new ByteArrayInputStream(bytes);

            amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, byteArrayIs, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            imgurl = amazonS3Client.getUrl(bucketName, fileName).toString();
        }

        Comment comment = Comment.builder()
                .user(userDetails.getUser())
                .consult(consult)
                .imgurl(imgurl)
                .build();

        commentrepository.save(comment);

        return Response.success(new CommentResponse(comment));
    }
    private void validRealtor(UserDetailsImpl userDetails){
        if(userDetails.getAuthority() != Authority.ROLE_REALTOR)
            throw new AccessDeniedException();
    }
}
