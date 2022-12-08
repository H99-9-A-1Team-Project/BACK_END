package com.example.backend.global.infra.S3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.backend.footsteps.dto.request.PhotoImgUrlDto;
import com.example.backend.footsteps.dto.request.PhotoListRequestDto;
import com.example.backend.footsteps.model.Photo;
import com.example.backend.global.exception.customexception.ImageNotFoundException;
import com.example.backend.global.security.auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@RequiredArgsConstructor
@Service
public class AmazonS3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Async
    public String upload(MultipartFile multipartFile, String dirName, String email) throws IOException {
        File file = convertMultipartFileToFile(multipartFile)
                .orElseThrow( ()-> new IllegalArgumentException("파일 업로드에 실패했습니다"));
        String key = randomFileName(email, dirName);
        String path = putS3(file, key);
        file.delete();

        return path;
    }

    public Optional<File> convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        // 일단 업로드된 파일을 프로젝트 내 폴더에 생성
        File file = new File(System.getProperty("user.dir") + "/" + multipartFile.getOriginalFilename());

        if (file.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(multipartFile.getBytes());
            }
            return Optional.of(file);
        }
        return Optional.empty();
    }

    public String randomFileName(String email, String dirName) {
        int idx = email.indexOf("@");
        return dirName + "/" + UUID.randomUUID() + email.substring(0,idx);
    }

    public String putS3(File uploadFile, String fileName) {
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return getS3(bucket, fileName);
    }

    public String getS3(String bucket, String fileName) {
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    public HashMap<String, String> uploadMultipleS3Photo(PhotoListRequestDto multipartFile, UserDetailsImpl userDetails) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(PhotoListRequestDto.class);
        HashMap<String, String> imgUrlList = new HashMap<>();

        for (PropertyDescriptor propertyDesc : beanInfo.getPropertyDescriptors()) {
            String propertyName = propertyDesc.getName(); // column 이름
            Object value = propertyDesc.getReadMethod().invoke(multipartFile); // column 값

            if(propertyName.contains("Img") && value != null) {
                MultipartFile file = (MultipartFile) value;
                if(!file.isEmpty()){
                    String imageUrl = upload((MultipartFile) value, "FootstepPhotos", userDetails.getUser().getEmail());
                    imgUrlList.put(propertyName,imageUrl);
                }
            }
        }

        return imgUrlList;
    }

}