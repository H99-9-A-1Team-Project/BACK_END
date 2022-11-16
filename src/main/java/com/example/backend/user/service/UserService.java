package com.example.backend.user.service;


import com.example.backend.global.config.auth.UserDetailsImpl;
import com.example.backend.global.entity.Authority;
import com.example.backend.global.exception.customexception.common.AccessDeniedException;
import com.example.backend.global.exception.customexception.user.UserUnauthorizedException;
import com.example.backend.user.dto.*;
import com.example.backend.global.entity.Realtor;
import com.example.backend.global.entity.User;
import com.example.backend.global.exception.customexception.user.MemberNotEqualsException;
import com.example.backend.global.exception.customexception.user.MemberNotFoundException;
import com.example.backend.user.repository.RealtorRepository;
import com.example.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RealtorRepository realtorRepository;

    @Transactional(readOnly = true)
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(UserDto.toDto(user));
        }
        return userDtos;
    }

    @Transactional(readOnly = true)
    public UserDto findUser(Long id) {
        return UserDto.toDto(userRepository.findById(id).orElseThrow(MemberNotFoundException::new));
    }


    @Transactional
    public void deleteUserInfo(User user, Long id) {
        User target = userRepository.findById(id).orElseThrow(MemberNotFoundException::new);

        if (user.equals(target)) {
            userRepository.deleteById(id);
        } else {
            throw new MemberNotEqualsException();
        }
    }

    @Transactional
    public void editUserNickname(NicknameRequestDto nicknameRequestDto, UserDetailsImpl userDetails){
        validAuth(userDetails);
        User user = userRepository.findByEmail(userDetails.getUser().getEmail()).orElseThrow();
        user.update(nicknameRequestDto.getNickname());
    }

    public Object getMyProfile(UserDetailsImpl userDetails) {
        validAuth(userDetails);
        Authority authority = userDetails.getUser().getAuthority();
        if(authority.equals(Authority.ROLE_USER)){
            return new UserProfileResponseDto(userDetails.getUser());
        }

        Realtor user = realtorRepository.findByEmail(userDetails.getUser().getEmail()).orElseThrow(MemberNotFoundException::new);
        return new RealtorProfileResponseDto(user);

    }

    public void validAuth(UserDetailsImpl userDetails){
        if(userDetails == null) throw new UserUnauthorizedException();
    }

}


