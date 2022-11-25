package com.example.backend.user.controller;

//import com.example.backend.global.response.Response;
import com.example.backend.user.dto.request.EmailConfirmRequestDto;
import com.example.backend.user.dto.request.SignUpMemberRequestDto;
import com.example.backend.user.dto.request.SignUpRealtorRequestDto;
import com.example.backend.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import java.io.IOException;

//import static com.example.backend.global.response.Response.success;


@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class AuthController {

    private final AuthService authService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public ResponseEntity<?> memberSignup(@Valid @RequestBody SignUpMemberRequestDto signUpMemberRequestDto) {
        authService.memberSignUp(signUpMemberRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/realtor/signup")
    public ResponseEntity<?> relatorSignup(@RequestPart(value = "content") SignUpRealtorRequestDto signUpRealtorRequestDto,
                                  @RequestPart(value = "license") MultipartFile multipartFile) throws IOException {
        authService.realtorSignUp(signUpRealtorRequestDto, multipartFile);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/emailconfirm")
    public ResponseEntity<?> emailConfirm(@RequestBody EmailConfirmRequestDto emailConfirmRequestDto){
        authService.emailConfirm(emailConfirmRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
