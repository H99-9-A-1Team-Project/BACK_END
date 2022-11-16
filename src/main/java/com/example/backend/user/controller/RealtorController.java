package com.example.backend.user.controller;


import com.example.backend.global.config.auth.UserDetailsImpl;
import com.example.backend.global.entity.User;
import com.example.backend.global.exception.customexception.user.MemberNotFoundException;
import com.example.backend.global.response.Response;
import com.example.backend.user.dto.IntroMessageDto;
import com.example.backend.user.dto.NicknameRequestDto;
import com.example.backend.user.dto.RealtorApproveDto;
import com.example.backend.user.repository.UserRepository;
import com.example.backend.user.service.RealtorService;
import com.example.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/v1")
@RequiredArgsConstructor
@RestController
public class RealtorController {

    private final RealtorService realtorService;

    // realtor 회원가입 승인 여부 수정 (관리자 권한)
    @PutMapping("/realtor-approval")
    public ResponseEntity approveRealtor(@RequestBody RealtorApproveDto dto,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        realtorService.approveRealtor(dto, userDetails);
        return new ResponseEntity(HttpStatus.OK);
    }

    // realtor 회원가입 승인 목록 (관리자 권한)
    @GetMapping("/realtor-approval")
    public ResponseEntity<?> getRealtorApprovalList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(realtorService.getRealtorApprovalList(userDetails));

    }

    @PutMapping("/realtor/edit-nickname")
    public Response editRealtorNickname(@RequestBody NicknameRequestDto nicknameRequestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails){
        realtorService.editRealtorNickname(nicknameRequestDto, userDetails);
        return Response.success();
    }

    @PutMapping("/realtor/intro-message")
    public Response editRealtorIntroMessage(@RequestBody IntroMessageDto introMessageDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails){
        realtorService.editRealtorIntroMessage(introMessageDto, userDetails);
        return Response.success();
    }

    @PutMapping("/realtor/profile-image")
    public ResponseEntity<?> editRealtorProfileImage(@RequestPart(value = "profile") MultipartFile multipartFile,
                                                     @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        realtorService.editRealtorProfileImage(multipartFile, userDetails);
        return new ResponseEntity(HttpStatus.OK);
    }
}