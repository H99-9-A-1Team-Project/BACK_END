package com.example.backend.user.service;


import com.example.backend.global.config.auth.UserDetailsImpl;
import com.example.backend.global.entity.Authority;
import com.example.backend.global.entity.Member;
import com.example.backend.global.exception.customexception.user.UserUnauthorizedException;
import com.example.backend.user.dto.*;
import com.example.backend.global.entity.Realtor;
import com.example.backend.global.entity.User;
import com.example.backend.global.exception.customexception.user.MemberNotFoundException;
import com.example.backend.user.repository.MemberRepository;
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
    private final MemberRepository memberRepository;

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
    public void deleteUserInfo(UserDetailsImpl userDetails) {
        validAuth(userDetails);
        userRepository.deleteByEmail(userDetails.getUser().getEmail());
    }

    @Transactional
    public void editUserNickname(editUserInfoRequestDto nicknameRequestDto, UserDetailsImpl userDetails){
        validAuth(userDetails);
        Member member = memberRepository.findByEmail(userDetails.getUser().getEmail());
        member.updateProfileImage(nicknameRequestDto);
        member.updateNickname(nicknameRequestDto);
    }

    public Object getMyProfile(UserDetailsImpl userDetails) {
        validAuth(userDetails);

        Authority authority = userDetails.getUser().getAuthority();

        if(authority.equals(Authority.ROLE_USER)){
            Member member = memberRepository.findByEmail(userDetails.getUser().getEmail());
            return new MemberProfileResponseDto(member);
        }

        Realtor realtor = realtorRepository.findByEmail(userDetails.getUser().getEmail()).orElseThrow(MemberNotFoundException::new);
        return new RealtorProfileResponseDto(realtor);

    }

    public void validAuth(UserDetailsImpl userDetails){
        if(userDetails == null) throw new UserUnauthorizedException();
    }

}


