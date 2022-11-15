package com.example.backend.footsteps.controller;

import com.example.backend.footsteps.dto.FootstepsRequstDto;
import com.example.backend.footsteps.dto.FootstepsResponseDto;
import com.example.backend.footsteps.dto.ResponseDto;
import com.example.backend.footsteps.service.FootstepsService;
import com.example.backend.global.config.auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class FootstepsController {
    private final FootstepsService footstepsService;

    @PostMapping("/post")
    private ResponseDto<FootstepsResponseDto> createPost(@RequestPart(required = false, value = "file") List<MultipartFile> multipartFile,
                                                         @RequestPart(value = "post") @Valid FootstepsRequstDto postRequestDto,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) throws IOException {
        return footstepsService.createPost(multipartFile, postRequestDto, userDetailsImpl.getUser());
    }
}
