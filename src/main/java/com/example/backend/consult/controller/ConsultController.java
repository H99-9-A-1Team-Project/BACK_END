package com.example.backend.consult.controller;

import com.example.backend.consult.dto.request.PutDetailConsultRequestDto;
import com.example.backend.consult.dto.request.RegisterConsultRequestDto;
import com.example.backend.consult.dto.response.DetailConsultResponseDto;
import com.example.backend.consult.dto.response.UserAllConsultResponseDto;
import com.example.backend.consult.service.ConsultService;
import com.example.backend.global.security.auth.UserDetailsImpl;
//import com.example.backend.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class ConsultController {
    private final ConsultService consultService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/advicerequest")
    public ResponseEntity<?> registerConsult(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                    @Valid @RequestBody RegisterConsultRequestDto dto) {
        consultService.registerConsult(userDetails, dto);
        return new ResponseEntity<>(HttpStatus.OK);
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
    public ResponseEntity<?> getRepliedConsult(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(consultService.getRepliedConsult(userDetails));
    }

    @GetMapping("/consult/{consult_id}")
    public ResponseEntity<?> detailConsult(@PathVariable("consult_id") Long consult_id,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails){
        DetailConsultResponseDto redDtos = consultService.detailConsult(consult_id, userDetails);
        return ResponseEntity.ok(redDtos);
    }
    @PutMapping("/consult/{consult_id}")
    public ResponseEntity<?> PutdetailConsult(@PathVariable("consult_id") Long consult_id,
                                           @RequestBody PutDetailConsultRequestDto dto,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails){
        DetailConsultResponseDto redDtos = consultService.PutdetailConsult(consult_id,dto,userDetails);
        return ResponseEntity.ok(redDtos);
    }


}
