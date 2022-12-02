package com.example.backend.user.controller;

import com.example.backend.global.config.jwt.JWTUtil;
import com.example.backend.global.security.auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
@Slf4j
public class RedirectController {

    private final JWTUtil jwtUtil;

    @GetMapping("/oauth")
    public String getOauthJWTToken(@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response) {
        response.setHeader("refresh_token", jwtUtil.makeRefreshToken(userDetails));
        response.setHeader("access_token", jwtUtil.makeAuthToken(userDetails));
        return "redirect:/";
    }
}
