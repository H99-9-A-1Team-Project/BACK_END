package com.example.backend.comment.controller;

import com.example.backend.comment.dto.ConsultMessageRequestDto;
import com.example.backend.comment.service.CommentService;
import com.example.backend.global.config.auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/consult/{consult_id}/img")
    public ResponseEntity<?> registerConsultCommentImg(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                   @PathVariable Long consult_id,
                                                   @RequestPart(value = "file") MultipartFile multipartFile) throws IOException {
        return ResponseEntity.ok(commentService.registerConsultCommentImg(userDetails, multipartFile));

    }

    @PostMapping("/consult/{consult_id}/comment")
    public ResponseEntity<?> registerConsultCommentMessage(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                           @PathVariable Long consult_id,
                                                           @RequestBody ConsultMessageRequestDto dto
    ){
        commentService.registerConsultCommentMessage(userDetails, consult_id, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
