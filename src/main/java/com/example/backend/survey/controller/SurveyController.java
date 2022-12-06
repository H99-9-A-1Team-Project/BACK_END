package com.example.backend.survey.controller;

import com.example.backend.global.security.auth.UserDetailsImpl;
import com.example.backend.survey.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class SurveyController {
    private final SurveyService surveyService;


    @GetMapping("/survey")
    private ResponseEntity<?> getUserSurvey(@AuthenticationPrincipal UserDetailsImpl userDetails, Pageable pageable
    ){
        return ResponseEntity.ok(surveyService.getUserSurvey(userDetails, pageable));

    }

}
