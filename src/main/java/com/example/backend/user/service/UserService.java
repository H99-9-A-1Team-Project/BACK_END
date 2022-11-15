package com.example.backend.user.service;


import com.example.backend.global.entity.Authority;
import com.example.backend.global.exception.customexception.common.AccessDeniedException;
import com.example.backend.user.dto.IntroMessageDto;
import com.example.backend.user.dto.NicknameRequestDto;
import com.example.backend.user.dto.RealtorApproveDto;
import com.example.backend.user.dto.UserDto;
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


//    @Transactional
//    public UserDto editUserInfo(Long id, UserDto updateInfo) {
//        User user = userRepository.findById(id).orElseThrow(MemberNotFoundException::new);
//
//        // 권한 처리
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (!authentication.getName().equals(user.getEmail())) {
//            throw new MemberNotEqualsException();
//        } else {
//            user.setNickname(updateInfo.getNickname());
//            return UserDto.toDto(user);
//        }
//    }

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
    public void editUserNickname(NicknameRequestDto nicknameRequestDto){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(nicknameRequestDto.getNickname()).orElseThrow(MemberNotFoundException::new);
        if(!authentication.getName().equals(user.getEmail())){
            throw new MemberNotEqualsException();
        }else{
            user.setNickname(nicknameRequestDto.getNickname());
        }
    }

    @Transactional
    public void editRealtorNickname(NicknameRequestDto nicknameRequestDto){
        Realtor realtor = realtorRepository.findByEmail(nicknameRequestDto.getNickname()).orElseThrow(MemberNotFoundException::new);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!authentication.getName().equals(realtor.getEmail())){
            throw new MemberNotEqualsException();
        }else{
            realtor.setNickname(nicknameRequestDto.getNickname());
        }
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

    @Transactional
    public void realtorApproval(RealtorApproveDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        validateManager(authentication);
        Realtor realtor = realtorRepository.findByEmail(dto.getEmail())
                .orElseThrow(MemberNotFoundException::new);

        realtor.update(dto);
    }

    private void validateManager(Authentication authentication) {
        User manager = userRepository.findByEmail(authentication.getName())
                .orElseThrow(AccessDeniedException::new);
        if(manager.getAuthority().equals(Authority.ROLE_ADMIN)){
            throw new AccessDeniedException();
        }
    }
}


