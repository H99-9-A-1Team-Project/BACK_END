package com.example.backend.controller;


import com.example.backend.dto.ResponseDto;
import com.example.backend.dto.request.LoginRequestDto;
import com.example.backend.dto.request.MemberRequestDto;
import com.example.backend.dto.response.LoginResponseDto;
import com.example.backend.dto.response.MemberResponseDto;
import com.example.backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<MemberResponseDto>> registerMember(@RequestBody @Valid MemberRequestDto memberRequestDto){
        return memberService.signup(memberRequestDto);
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<ResponseDto<LoginResponseDto>> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse){
        return memberService.login(loginRequestDto, httpServletResponse);
    }

}
