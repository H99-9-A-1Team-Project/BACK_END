package com.example.backend.user.controller;

import com.example.backend.user.dto.LoginRequestDto;
import com.example.backend.user.dto.SignUpRealtorRequestDto;
import com.example.backend.user.dto.SignUpRequestDto;
import com.example.backend.user.dto.TokenRequestDto;
import com.example.backend.user.repository.UserRepository;
import com.example.backend.global.response.Response;
import com.example.backend.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.backend.global.response.Response.success;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthController {
    private final UserRepository userRepository;
    private final AuthService authService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public Response memberSignup(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        authService.memberSignUp(signUpRequestDto);
        return success();
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/realtor/signup")
    public Response relatorSignup(@Valid @RequestBody SignUpRealtorRequestDto signUpRealtorRequestDto) {
        authService.realtorSignUp(signUpRealtorRequestDto);
        return success();
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> signIn(@Valid @RequestBody LoginRequestDto loginRequestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("access-token", authService.login(loginRequestDto).getAccessToken());
        headers.add("refresh-token", authService.login(loginRequestDto).getRefreshToken());

        return ResponseEntity.ok()
                .headers(headers).build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/reissue")
    public Response reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return success(authService.reissue(tokenRequestDto));
    }

}