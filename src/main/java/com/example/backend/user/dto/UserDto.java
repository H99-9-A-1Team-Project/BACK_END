package com.example.backend.user.dto;

import com.example.backend.user.model.User;
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


}
