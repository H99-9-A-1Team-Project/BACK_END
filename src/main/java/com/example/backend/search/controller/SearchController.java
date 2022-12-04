package com.example.backend.search.controller;

import com.example.backend.global.security.auth.UserDetailsImpl;
import com.example.backend.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/consult/search")
    public ResponseEntity<?> searchConsult(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                           @RequestParam(value = "keyword")String keyword) {
        return ResponseEntity.ok(searchService.searchConsult(userDetails, keyword));
    }
}
