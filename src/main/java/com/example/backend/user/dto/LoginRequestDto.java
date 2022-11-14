package com.example.backend.user.dto;

import com.example.backend.global.config.auth.UserDetailsImpl;
import com.example.backend.global.entity.Authority;
import com.example.backend.global.entity.User;
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

    public UsernamePasswordAuthenticationToken toAuthentication(User user) {
        UserDetailsImpl details = new UserDetailsImpl(user);
        return new UsernamePasswordAuthenticationToken(details.getUser().getEmail(), email);
    }
}
