package com.example.backend.user.dto;

import com.example.backend.global.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String email; // 로그인 아이디
    private String Nickname; // 유저 실명

    public static UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getNickname());
    }

}
