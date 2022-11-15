package com.example.backend.footsteps.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.example.backend.footsteps.dto.FootstepsRequstDto;
import com.example.backend.footsteps.dto.FootstepsResponseDto;
import com.example.backend.footsteps.dto.PhotoResponseDto;
import com.example.backend.footsteps.dto.ResponseDto;
import com.example.backend.footsteps.repository.FootstepsRepository;
import com.example.backend.footsteps.repository.PhotoRepository;
import com.example.backend.global.config.S3.CommonUtils;
import com.example.backend.global.entity.FootstepsPost;
import com.example.backend.global.entity.Photo;
import com.example.backend.global.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FootstepsService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private final AmazonS3Client amazonS3Client;
    private final PhotoRepository photoRepository;

    private final FootstepsRepository footstepsRepository;
    public ResponseDto<FootstepsResponseDto> createPost(List<MultipartFile> multipartFile, FootstepsRequstDto postRequestDto, User user) throws IOException {
        String imgurl = null;

        FootstepsPost footstepsPost = FootstepsPost.builder()
                .title(postRequestDto.getTitle())
                .user(user)
                .build();
        footstepsRepository.save(footstepsPost);
        for (MultipartFile file : multipartFile){
            if (!multipartFile.isEmpty()) {
                String fileName = CommonUtils.buildFileName(file.getOriginalFilename());
                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentType(file.getContentType());

                byte[] bytes = IOUtils.toByteArray(file.getInputStream());
                objectMetadata.setContentLength(bytes.length);
                ByteArrayInputStream byteArrayIs = new ByteArrayInputStream(bytes);

                amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, byteArrayIs, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
                imgurl = amazonS3Client.getUrl(bucketName, fileName).toString();

                Photo photo = new Photo(imgurl, footstepsPost);
//                post.addPhoto(photo);
                photoRepository.save(photo);

                //추가
            }
        }
        /*
         * 확인용
         * */
        List<Photo> imgList = photoRepository.findAllByFootstepsPost(footstepsPost.getId());
        List<PhotoResponseDto> photoResponseDto = new ArrayList<>();
        for(Photo photo : imgList){
            photoResponseDto.add(
                    PhotoResponseDto.builder()
                            .postImgUrl(photo.getPostImgUrl())
                            .build()
            );
        }
        return ResponseDto.success(
                FootstepsResponseDto.builder()
                        .title(postRequestDto.getTitle())
                        .postImgUrl(photoResponseDto)
                        .build()
        );
    }
}
