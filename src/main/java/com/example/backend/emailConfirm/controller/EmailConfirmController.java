package com.example.backend.emailConfirm.controller;

import com.example.backend.emailConfirm.dto.EmailConfirmRequestDto;
import com.example.backend.emailConfirm.service.EmailService;
import com.example.backend.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class EmailConfirmController {

    private final EmailService emailService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/emailconfirm")
    public Response emailConfirm(@RequestBody EmailConfirmRequestDto emailConfirmRequestDto){
        emailService.emailConfirm(emailConfirmRequestDto);
        return Response.success();
    }
}
