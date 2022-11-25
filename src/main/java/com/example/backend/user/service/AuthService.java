package com.example.backend.user.service;

import com.example.backend.global.infra.S3.service.AmazonS3Service;
import com.example.backend.global.infra.S3.dto.AwsS3;
//import com.example.backend.global.response.Response;
import com.example.backend.user.dto.request.EmailConfirmRequestDto;
import com.example.backend.user.dto.request.SignUpMemberRequestDto;
import com.example.backend.user.dto.request.SignUpRealtorRequestDto;
import com.example.backend.user.model.Member;
import com.example.backend.user.model.Realtor;
import com.example.backend.user.exception.register.MemberEmailAlreadyExistsException;
import com.example.backend.user.repository.RealtorRepository;
import com.example.backend.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RealtorRepository realtorRepository;
    private final PasswordEncoder passwordEncoder;
    private final AmazonS3Service amazonS3Service;

    @Value("${cloud.aws.credentials.domain}")
    private String amazonS3Domain;

    @Transactional
    public void memberSignUp(SignUpMemberRequestDto signUpMemberRequestDto) {
        validateMemberSignUpInfo(signUpMemberRequestDto);
        signUpMemberRequestDto.setPassword(passwordEncoder.encode(signUpMemberRequestDto.getPassword()));
        Member user = new Member(signUpMemberRequestDto);
        userRepository.save(user);
    }

    @Transactional
    public void realtorSignUp(SignUpRealtorRequestDto signUpRealtorRequestDto, MultipartFile multipartFile) throws IOException {
        validateRealtorSignUpInfo(signUpRealtorRequestDto);
        signUpRealtorRequestDto.setPassword(passwordEncoder.encode(signUpRealtorRequestDto.getPassword()));

        String imageUrl = amazonS3Service.upload(multipartFile, "realtor-authentication", signUpRealtorRequestDto.getEmail());

        Realtor realtor = new Realtor(signUpRealtorRequestDto);
        realtor.setLicense(imageUrl);
        realtorRepository.save(realtor);
    }
    @Transactional
    public void emailConfirm(EmailConfirmRequestDto emailConfirmRequestDto) {
        if (realtorRepository.findByEmail(emailConfirmRequestDto.getEmail()).isPresent()) {
            throw new MemberEmailAlreadyExistsException();
        }
    }

    private void validateMemberSignUpInfo(SignUpMemberRequestDto signUpRequestDto) {
        if (userRepository.existsByEmail(signUpRequestDto.getEmail()))
            throw new MemberEmailAlreadyExistsException();
    }
    private void validateRealtorSignUpInfo(SignUpRealtorRequestDto signUpRealtorRequestDto) {
        if (userRepository.existsByEmail(signUpRealtorRequestDto.getEmail()))
            throw new MemberEmailAlreadyExistsException();
    }




}
