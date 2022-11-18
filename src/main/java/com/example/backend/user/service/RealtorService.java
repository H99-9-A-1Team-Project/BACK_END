package com.example.backend.user.service;


import com.example.backend.global.S3.dto.AwsS3;
import com.example.backend.global.S3.service.AmazonS3Service;
import com.example.backend.global.config.auth.UserDetailsImpl;
import com.example.backend.global.entity.Authority;
import com.example.backend.global.entity.Realtor;
import com.example.backend.global.entity.User;
import com.example.backend.global.exception.customexception.common.AccessDeniedException;
import com.example.backend.global.exception.customexception.user.MemberNotFoundException;
import com.example.backend.global.exception.customexception.user.UserUnauthorizedException;
//import com.example.backend.mail.MailDto;
//import com.example.backend.mail.MailService;
import com.example.backend.user.dto.*;
import com.example.backend.user.repository.RealtorRepository;
import com.example.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RealtorService {

    private final UserRepository userRepository;
    private final RealtorRepository realtorRepository;
    private final AmazonS3Service amazonS3Service;
//    private final MailService mailService;

    @Value("${cloud.aws.credentials.domain}")
    private String amazonS3Domain;

//    @Transactional
//    public void approveRealtor(RealtorApproveDto dto, UserDetailsImpl userDetails) {
//        validateManager(userDetails);
//        Realtor realtor = realtorRepository.findByEmail(dto.getEmail()).orElseThrow(MemberNotFoundException::new);
//        mailService.sendSimpleMessage(new MailDto(realtor.getEmail(), "축하합니다, 등대지기 가입이 승인되었습니다.", "축하합니다"));
//        realtor.update(dto);
//    }

    @Transactional(readOnly = true)
    public List<RealtorListResponseDto> getRealtorList(UserDetailsImpl userDetails) {
        validateManager(userDetails);
        List<Realtor> realtorList = realtorRepository.findAll();
        return realtorList.stream().map(RealtorListResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public void editRealtorNickname(NicknameRequestDto nicknameRequestDto, UserDetailsImpl userDetails){
        validAuth(userDetails);
        User user = userRepository.findByEmail(userDetails.getUser().getEmail()).orElseThrow();
        user.update(nicknameRequestDto.getNickname());
    }

    @Transactional
    public void editRealtorIntroMessage(IntroMessageDto introMessageDto, UserDetailsImpl userDetails){
        validAuth(userDetails);
        validRealtor(userDetails);
        Realtor realtor = realtorRepository.findByEmail(userDetails.getUser().getEmail()).orElseThrow();
        realtor.update(introMessageDto);
    }

    private void validateManager(UserDetailsImpl userDetails) {
        if(userDetails == null){ throw new UserUnauthorizedException(); }
        User manager = userRepository.findByEmail(userDetails.getUser().getEmail()).orElseThrow(AccessDeniedException::new);
        if(!manager.getAuthority().equals(Authority.ROLE_ADMIN)){
            throw new AccessDeniedException();
        }
    }

    public void editRealtorProfileImage(MultipartFile multipartFile, UserDetailsImpl userDetails) throws IOException {
        Realtor realtor = validRealtor(userDetails);

        AwsS3 image = amazonS3Service.upload(multipartFile, "realtor-authentication");
        String imageUrl = amazonS3Domain + URLEncoder.encode(image.getKey(), StandardCharsets.US_ASCII);

        realtor.setProfile(imageUrl);
        realtorRepository.save(realtor);
    }

    public void validAuth(UserDetailsImpl userDetails){
        if(userDetails == null) throw new UserUnauthorizedException();
    }

    public Realtor validRealtor(UserDetailsImpl userDetails){
        return realtorRepository.findByEmail(userDetails.getUser().getEmail())
                .orElseThrow(AccessDeniedException::new);
    }
}


