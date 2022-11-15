package com.example.backend.user.service;

import com.example.backend.global.entity.Member;
import com.example.backend.user.dto.*;
import com.example.backend.global.entity.Realtor;
import com.example.backend.global.exception.customexception.register.MemberEmailAlreadyExistsException;
import com.example.backend.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void memberSignUp(SignUpMemberRequestDto signUpMemberRequestDto) {
        validateMemberSignUpInfo(signUpMemberRequestDto);
        signUpMemberRequestDto.setPassword(passwordEncoder.encode(signUpMemberRequestDto.getPassword()));
        Member user = new Member(signUpMemberRequestDto);
        userRepository.save(user);
    }

    @Transactional
    public void realtorSignUp(SignUpRealtorRequestDto signUpRealtorRequestDto) {
        validateRealtorSignUpInfo(signUpRealtorRequestDto);
        signUpRealtorRequestDto.setPassword(passwordEncoder.encode(signUpRealtorRequestDto.getPassword()));
        Realtor realtor = new Realtor(signUpRealtorRequestDto);
        userRepository.save(realtor);
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
