package com.example.backend.Comment.controller;

import com.example.backend.Comment.service.CommentService;
import com.example.backend.global.config.auth.UserDetailsImpl;
import com.example.backend.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/consult/{consult_id}/img")
    private Response createCommentImg(@RequestPart(required = false, value = "img") MultipartFile multipartFile,
                                @PathVariable("consult_id") Long consultId,
                                @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return Response.success(commentService.createCommentImg(multipartFile, consultId, userDetails));
    }
}
