package com.example.backend.service.user;


import com.example.backend.dto.user.IntroMessageDto;
import com.example.backend.dto.user.NicknameRequestDto;
import com.example.backend.dto.user.UserDto;
import com.example.backend.entity.user.Realtor;
import com.example.backend.entity.user.User;
import com.example.backend.exception.MemberNotEqualsException;
import com.example.backend.exception.MemberNotFoundException;
import com.example.backend.repository.user.RealtorRepository;
import com.example.backend.repository.user.UserRepository;
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
}

