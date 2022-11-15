package com.example.backend.user.service;


import com.example.backend.global.config.auth.UserDetailsImpl;
import com.example.backend.global.exception.customexception.user.UserUnauthorizedException;
import com.example.backend.user.dto.*;
import com.example.backend.global.entity.Realtor;
import com.example.backend.global.entity.User;
import com.example.backend.global.exception.customexception.user.MemberNotEqualsException;
import com.example.backend.global.exception.customexception.user.MemberNotFoundException;
import com.example.backend.user.repository.RealtorRepository;
import com.example.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        if(userDetails == null) throw new UserUnauthorizedException();
        User user = userRepository.findByEmail(userDetails.getUser().getEmail()).orElseThrow();
        user.update(nicknameRequestDto.getNickname());
    }

    @Transactional
    public void editRealtorNickname(NicknameRequestDto nicknameRequestDto, UserDetailsImpl userDetails){
        if(userDetails == null) throw new UserUnauthorizedException();
        User user = userRepository.findByEmail(userDetails.getUser().getEmail()).orElseThrow();
        user.update(nicknameRequestDto.getNickname());
    }

    @Transactional
    public void editRealtorIntroMessage(IntroMessageDto introMessageDto){
        Realtor realtor = realtorRepository.findByEmail(introMessageDto.getIntroMessage()).orElseThrow(MemberNotFoundException::new);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!authentication.getName().equals(realtor.getEmail())){
            throw new MemberNotEqualsException();
        }else{
            realtor.setNickname(introMessageDto.getIntroMessage());
        }
    }

}


