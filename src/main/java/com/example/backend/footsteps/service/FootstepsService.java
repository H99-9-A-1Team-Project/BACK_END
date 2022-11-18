package com.example.backend.footsteps.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.example.backend.footsteps.dto.FootstepsRequstDto;
import com.example.backend.footsteps.repository.FootstepsRepository;
import com.example.backend.footsteps.repository.PhotoRepository;
import com.example.backend.global.config.S3.CommonUtils;
import com.example.backend.global.config.auth.UserDetailsImpl;
import com.example.backend.global.entity.FootstepsPost;
import com.example.backend.global.entity.Photo;
import com.example.backend.global.exception.customexception.user.UserUnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Transactional
    public void createPost(List<MultipartFile> multipartFile, FootstepsRequstDto postRequestDto, UserDetailsImpl userDetails) throws IOException {
        validAuth(userDetails);

        List<Photo> photos = new ArrayList<>();

        FootstepsPost footstepsPost = saveFootStepPost(postRequestDto, userDetails);
        List<String> imgUrlList = uploadS3Photo(multipartFile);

        for (String imgUrl : imgUrlList) {
            photos.add(new Photo(imgUrl, footstepsPost));
        }

        photoRepository.saveAll(photos);

    }

    private List<String> uploadS3Photo(List<MultipartFile> multipartFile) throws IOException {
        List<String> imgUrlList = new ArrayList<>();

        for (MultipartFile file : multipartFile) {
            if (!multipartFile.isEmpty()) {
                String fileName = CommonUtils.buildFileName(file.getOriginalFilename());
                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentType(file.getContentType());

                byte[] bytes = IOUtils.toByteArray(file.getInputStream());
                objectMetadata.setContentLength(bytes.length);
                ByteArrayInputStream byteArrayIs = new ByteArrayInputStream(bytes);

                amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, byteArrayIs, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
                imgUrlList.add(amazonS3Client.getUrl(bucketName, fileName).toString());
            }
        }
        return imgUrlList;
    }

    private FootstepsPost saveFootStepPost(FootstepsRequstDto postRequestDto, UserDetailsImpl userDetails) {
        FootstepsPost footstepsPost = FootstepsPost.builder()
                .title(postRequestDto.getTitle())
                .coordFY(postRequestDto.getCoordFY())
                .coordFX(postRequestDto.getCoordFX())
                .price(postRequestDto.getPrice())
                .size(postRequestDto.getSize())
                .review(postRequestDto.getReview())
                .sun(postRequestDto.isSun())
                .mold(postRequestDto.isMold())
                .vent(postRequestDto.isVent())
                .water(postRequestDto.isWater())
                .ventil(postRequestDto.isVentil())
                .drain(postRequestDto.isDrain())
                .draft(postRequestDto.isDraft())
                .extraMemo(postRequestDto.getExtraMemo())
                .option(postRequestDto.getOption())
                .destroy(postRequestDto.isDestroy())
                .utiRoom(postRequestDto.isUtiRoom())
                .securityWindow(postRequestDto.isSecurityWindow())
                .noise(postRequestDto.isNoise())
                .loan(postRequestDto.isLoan())
                .cctv(postRequestDto.isCctv())
                .hill(postRequestDto.isHill())
                .mart(postRequestDto.isMart())
                .hospital(postRequestDto.isHospital())
                .accessibility(postRequestDto.isAccessibility())
                .park(postRequestDto.isPark())
                .createDate(LocalDateTime.now())
                .user(userDetails.getUser())
                .build();
        footstepsRepository.save(footstepsPost);
        return footstepsPost;
    }

    public void validAuth(UserDetailsImpl userDetails){
        if(userDetails == null) throw new UserUnauthorizedException();
    }
}