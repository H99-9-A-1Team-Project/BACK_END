package com.example.backend.consult.controller;

import com.example.backend.consult.dto.RegisterConsultDto;
import com.example.backend.consult.dto.UserAllConsultResponseDto;
import com.example.backend.consult.service.ConsultService;
import com.example.backend.footsteps.dto.FootstepsRequstDto;
import com.example.backend.footsteps.dto.ResponseDto;
import com.example.backend.global.config.auth.UserDetailsImpl;
import com.example.backend.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class ConsultController {
    private final ConsultService consultService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/advicerequest")
    public Response registerConsult(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                    @Valid @RequestBody RegisterConsultDto dto) {
        consultService.registerConsult(userDetails, dto);
        return Response.success();
    }
    @GetMapping("/myconsult")
    public ResponseEntity<?> allConsult(@AuthenticationPrincipal UserDetailsImpl userDetails){
        List<UserAllConsultResponseDto> redDtos = consultService.allConsult(userDetails.getUser().getId());
        return ResponseEntity.ok(redDtos);
    }
    @GetMapping("/waitcustomer")
    public ResponseEntity<?> waitConsult(@AuthenticationPrincipal UserDetailsImpl userDetails){
    List<UserAllConsultResponseDto> redDtos = consultService.waitConsult(userDetails);
        return ResponseEntity.ok(redDtos);
    }
    @GetMapping("/replied")
    public ResponseEntity<?> repliedConsult(@AuthenticationPrincipal UserDetailsImpl userDetails){
        List<UserAllConsultResponseDto> redDtos = consultService.repliedConsult(userDetails);
        return ResponseEntity.ok(redDtos);
    }


}
