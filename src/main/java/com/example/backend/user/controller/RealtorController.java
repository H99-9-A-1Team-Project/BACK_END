package com.example.backend.user.controller;


import com.example.backend.global.config.auth.UserDetailsImpl;
import com.example.backend.global.response.Response;
import com.example.backend.user.dto.*;
import com.example.backend.user.service.RealtorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    // realtor 회원가입 요청 목록 조회 (관리자 권한)
    @GetMapping("/realtor-approval")
    public ResponseEntity<?> getRealtorApprovalList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(realtorService.getRealtorList(userDetails));

    }

    // realtor 프로필 수정
    @PutMapping("/realtor/profile")
    public ResponseEntity<?> editRealtorProfile(@RequestPart(value = "profile") MultipartFile multipartFile,
                                                @RequestPart(value = "content") RealtorEditRequestDto realtorEditRequestDto,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        realtorService.editRealtorProfile(multipartFile, realtorEditRequestDto, userDetails);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}