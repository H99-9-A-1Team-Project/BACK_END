package com.example.backend.footsteps.controller;

import com.example.backend.footsteps.dto.response.FootstepsDetailResponseDto;
import com.example.backend.footsteps.dto.request.FootstepsRequstDto;
import com.example.backend.footsteps.service.FootstepsService;
import com.example.backend.global.security.auth.UserDetailsImpl;
import com.example.backend.footsteps.model.FootstepsPost;
//import com.example.backend.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private ResponseEntity<?> createPost(@RequestPart(required = false, value = "file") List<MultipartFile> multipartFile,
                                @RequestPart(value = "post") @Valid FootstepsRequstDto postRequestDto,
                                @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        footstepsService.createPost(multipartFile, postRequestDto, userDetails);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/premises/allpost")
    private ResponseEntity<List<FootstepsPost>> getMyPosts(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(footstepsService.getMyPosts(userDetails));
    }

    @GetMapping("/premises/advicerrequest")
    private ResponseEntity<?> getMyAdviceRequest(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return  ResponseEntity.ok(footstepsService.getMyAdviceRequest(userDetails));

    }
    @GetMapping("/premises/{premisesId}/detail")
    public ResponseEntity<?> getFootstepDetail(@PathVariable("premisesId") Long premisesId,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails){
        FootstepsDetailResponseDto redDtos = footstepsService.getFootstepDetail(premisesId, userDetails);
        return ResponseEntity.ok(redDtos);
    }

    @GetMapping("/premises/{premisesId}")
    private ResponseEntity<?> getFootstepDetailImages(@PathVariable Long premisesId,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                      Pageable pageable
    ){
        return ResponseEntity.ok(footstepsService.getFootstepDetailImages(premisesId, userDetails, pageable));

    }

}
