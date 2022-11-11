package com.example.backend.controller.user;

import com.example.backend.dto.user.*;
import com.example.backend.repository.user.UserRepository;
import com.example.backend.response.Response;
import com.example.backend.service.user.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.backend.response.Response.success;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthController {
    private final UserRepository userRepository;
    private final AuthService authService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public Response signup(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        authService.signup(signUpRequestDto);
        return success();
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/realtor/signup")
    public Response signup2(@Valid @RequestBody SignUpRealtorRequestDto signUpRealtorRequestDto) {
        authService.signup2(signUpRealtorRequestDto);
        return success();
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public Response signIn(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return success(authService.login(loginRequestDto));
    }




    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/reissue")
    public Response reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return success(authService.reissue(tokenRequestDto));
    }

}
