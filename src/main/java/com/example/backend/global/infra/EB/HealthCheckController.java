package com.example.backend.global.infra.EB;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class HealthCheckController {


    @GetMapping("/awsHealthCheck")
    public ResponseEntity<Object> awsHealthyCheck(){
        return ResponseEntity.ok("awsHealthCheck - ok");
    }
}