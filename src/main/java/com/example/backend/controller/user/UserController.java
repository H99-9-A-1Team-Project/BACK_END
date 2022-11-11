package com.example.backend.controller.user;


import com.example.backend.dto.user.IntroMessageDto;
import com.example.backend.dto.user.NicknameRequestDto;
import com.example.backend.dto.user.UserDto;
import com.example.backend.entity.user.User;
import com.example.backend.exception.MemberNotFoundException;
import com.example.backend.repository.user.UserRepository;
import com.example.backend.response.Response;
import com.example.backend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
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

//    @ResponseStatus(HttpStatus.OK)
//    @PutMapping("/users/{id}")
//    public Response editUserInfo(@PathVariable Long id, @RequestBody UserDto userDto) {
//        return Response.success(userService.editUserInfo(id, userDto));
//    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/users/{id}")
    public Response deleteUserInfo(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(MemberNotFoundException::new);

        userService.deleteUserInfo(user, id);
        return Response.success();
    }

    @PutMapping("/user/editNickname")
    public Response editUserNickname(@RequestBody NicknameRequestDto nicknameRequestDto){
        userService.editUserNickname(nicknameRequestDto);
        return Response.success();
    }

    @PutMapping("/realtor/editNickname")
    public Response editRealtorNickname(@RequestBody NicknameRequestDto nicknameRequestDto){
        userService.editRealtorNickname(nicknameRequestDto);
        return Response.success();
    }

    @PutMapping("/realtor/introMessage")
    public Response editRealtorIntroMessage(@RequestBody IntroMessageDto introMessageDto){
        userService.editRealtorIntroMessage(introMessageDto);
        return Response.success();
    }
}