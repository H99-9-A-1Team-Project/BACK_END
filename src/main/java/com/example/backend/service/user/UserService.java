package com.example.backend.service.user;


import com.example.backend.dto.user.UserDto;
import com.example.backend.entity.user.User;
import com.example.backend.exception.MemberNotEqualsException;
import com.example.backend.exception.MemberNotFoundException;
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
    public UserDto editUserInfo(Long id, UserDto updateInfo) {
        User user = userRepository.findById(id).orElseThrow(MemberNotFoundException::new);

        // 권한 처리
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.getName().equals(user.getEmail())) {
            throw new MemberNotEqualsException();
        } else {
            user.setNickname(updateInfo.getNickname());
            return UserDto.toDto(user);
        }
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
}

