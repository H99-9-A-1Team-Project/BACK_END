package com.example.backend.user.controller;


import com.example.backend.global.security.auth.UserDetailsImpl;
import com.example.backend.user.dto.request.editUserInfoRequestDto;
import com.example.backend.user.repository.UserRepository;
//import com.example.backend.global.response.Response;
import com.example.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/user")
    public ResponseEntity<?> deleteUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.deleteUserInfo(userDetails);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/user/profile")
    public ResponseEntity<?> editUserNickname(@RequestBody editUserInfoRequestDto nicknameRequestDto,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails){
        userService.editUserNickname(nicknameRequestDto, userDetails);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/myprofile")
    public ResponseEntity<?> getMyProfile(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(userService.getMyProfile(userDetails));


    }

}