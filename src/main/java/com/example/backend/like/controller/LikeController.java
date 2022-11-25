package com.example.backend.like.controller;

import com.example.backend.consult.model.Consult;
import com.example.backend.global.security.auth.UserDetailsImpl;
import com.example.backend.like.service.LikeService;
import com.example.backend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class LikeController {
    private final LikeService likeService;
    @PostMapping("/like/{comment_id}")
    public ResponseEntity<?> likeComment(@PathVariable("comment_id") Long id,
                                         @AuthenticationPrincipal UserDetailsImpl UserDetails)
    {
        likeService.likeComment(id, UserDetails);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
