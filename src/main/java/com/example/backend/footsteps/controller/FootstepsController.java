package com.example.backend.footsteps.controller;

import com.example.backend.footsteps.dto.FootstepsRequstDto;
import com.example.backend.footsteps.dto.FootstepsResponseDto;
import com.example.backend.footsteps.dto.ResponseDto;
import com.example.backend.footsteps.service.FootstepsService;
import com.example.backend.global.config.auth.UserDetailsImpl;
import com.example.backend.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class FootstepsController {
    private final FootstepsService footstepsService;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/premises")
    private Response createPost(@RequestPart(required = false, value = "file") List<MultipartFile> multipartFile,
                                @RequestPart(value = "post") @Valid FootstepsRequstDto postRequestDto,
                                @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        footstepsService.createPost(multipartFile, postRequestDto, userDetails);
        return Response.success();
    }
}
