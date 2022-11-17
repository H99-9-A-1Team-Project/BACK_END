package com.example.backend.consult.controller;

import com.example.backend.global.config.auth.UserDetailsImpl;
import com.example.backend.consult.dto.RegisterConsultDto;
import com.example.backend.consult.service.ConsultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class ConsultController {
    private final ConsultService consultService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/advicerequest")
    public ResponseEntity<?> registerConsult(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                             @Valid @RequestBody RegisterConsultDto dto) {
        return consultService.registerConsult(userDetails, dto);
    }


}
