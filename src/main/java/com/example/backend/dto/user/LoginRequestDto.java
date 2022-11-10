package com.example.backend.dto.user;


import com.example.backend.entity.user.Authority;
import com.example.backend.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

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


    public User toMember(PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .authority(Authority.ROLE_USER)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
