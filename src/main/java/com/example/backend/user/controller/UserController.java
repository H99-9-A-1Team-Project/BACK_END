package com.example.backend.user.controller;


import com.example.backend.global.config.auth.UserDetailsImpl;
import com.example.backend.user.dto.editUserInfoRequestDto;
import com.example.backend.user.repository.UserRepository;
import com.example.backend.global.response.Response;
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
    @GetMapping("/users")
    public Response findAllUsers() {
        return Response.success(userService.findAllUsers());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users/{id}")
    public Response findUser(@PathVariable Long id) {
        return Response.success(userService.findUser(id));
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/user")
    public Response deleteUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.deleteUserInfo(userDetails);
        return Response.success();
    }

    @PutMapping("/user/profile")
    public Response editUserNickname(@RequestBody editUserInfoRequestDto nicknameRequestDto,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails){
        userService.editUserNickname(nicknameRequestDto, userDetails);
        return Response.success();
    }


    @GetMapping("/myprofile")
    public ResponseEntity<?> getMyProfile(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(userService.getMyProfile(userDetails));


    }

}