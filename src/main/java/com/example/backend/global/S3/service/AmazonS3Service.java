package com.example.backend.global.S3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.backend.global.S3.dto.AwsS3;
import com.example.backend.global.config.auth.UserDetailsImpl;
import com.example.backend.global.exception.customexception.common.ImageNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AmazonS3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile multipartFile, String dirName, String email) throws IOException {
        File file = convertMultipartFileToFile(multipartFile)
                .orElseThrow( ()-> new IllegalArgumentException("파일 업로드에 실패했습니다"));
        String key = randomFileName(email, dirName);
        String path = putS3(file, key);
        file.delete();

        return path;
    }

    public Optional<File> convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
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

    public List<String> uploadMultipleS3Photo(List<MultipartFile> multipartFile, UserDetailsImpl userDetails) throws IOException {
        List<String> imgUrlList = new ArrayList<>();

        for (MultipartFile file : multipartFile) {
            if(multipartFile == null || multipartFile.isEmpty()){
                throw new ImageNotFoundException();
            }
            String imageUrl = upload(file, "FootstepPhotos", userDetails.getUser().getEmail());
            imgUrlList.add(imageUrl);
        }
        return imgUrlList;
    }


}