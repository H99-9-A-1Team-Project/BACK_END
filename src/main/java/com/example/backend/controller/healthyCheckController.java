package com.example.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class healthyCheckController {


    @GetMapping("/")
    public ResponseEntity<Object> awsHealthyCheck(){
        return ResponseEntity.ok("awsHealthyCheck - ok");
    }
}