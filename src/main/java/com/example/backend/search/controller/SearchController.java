package com.example.backend.search.controller;

import com.example.backend.global.security.auth.UserDetailsImpl;
import com.example.backend.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class SearchController {

    private final SearchService searchService;
    @GetMapping( "/myconsult/search")
    public ResponseEntity<?> searchConsult(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                           @RequestParam(value = "keyword") String keyword) {
        return ResponseEntity.ok(searchService.searchConsult(userDetails, keyword));
    }

    @GetMapping("/premises/advicerequest/search")
    public ResponseEntity<?> searchpremises(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                           @RequestParam(value = "keyword")String keyword) {
        return ResponseEntity.ok(searchService.searchPremises(userDetails, keyword));
    }

    @GetMapping("/waitcustomer/search")
    public ResponseEntity<?> waitCustomerSearch(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                           @RequestParam(value = "keyword")String keyword) {
        return ResponseEntity.ok(searchService.waitCustomerSearch(userDetails, keyword));
    }

    @GetMapping("/replied/search")
    public ResponseEntity<?> repliedSearch(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                           @RequestParam(value = "keyword")String keyword) {
        return ResponseEntity.ok(searchService.repliedSearch(userDetails, keyword));
    }
}
