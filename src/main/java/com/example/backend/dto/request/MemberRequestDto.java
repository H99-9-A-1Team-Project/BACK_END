package com.example.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDto {

    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#$%^&*()+|=]{8,16}";

    @NotBlank(message = "Username은 공백일 수 없습니다.")
    //@Pattern(regexp = "[a-zA-Z0-9]{4,12}", message = "닉네임양식을 확인해주세요!")
    @Size(min= 4, max =12)//아이디 4~12 글자
    private String username;

    @Pattern(regexp = PASSWORD_REGEX, message = "패스워드는 무조건 영문, 숫자를 1글자 이상 포함해야 합니다.")
    @NotBlank(message = "Password는 공백일 수 없습니다.")
    private String password;

    @NotBlank
    private String passwordConfirm;

    public void setEncodePwd(String encodePwd) {
        this.password = encodePwd;
    }
}
