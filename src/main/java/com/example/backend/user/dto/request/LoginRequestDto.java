package com.example.backend.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginRequestDto {

    @NotBlank(message = "아이디를 입력해주세요")
    @Email(message = "이메일 형식만 가능합니다")
    private String email;


    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

}
