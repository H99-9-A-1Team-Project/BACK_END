package com.example.backend.user.service;

import com.example.backend.user.dto.*;
import com.example.backend.global.entity.Realtor;
import com.example.backend.global.entity.RefreshToken;
import com.example.backend.global.entity.User;
import com.example.backend.global.exception.customexception.LoginFailureException;
import com.example.backend.global.exception.customexception.MemberEmailAlreadyExistsException;
import com.example.backend.user.repository.RefreshTokenRepository;
import com.example.backend.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.backend.global.entity.Authority.ROLE_REALTOR;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void memberSignUp(SignUpRequestDto signUpRequestDto) {
        validateMemberSignUpInfo(signUpRequestDto);
        signUpRequestDto.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        User user = new User(signUpRequestDto);
        userRepository.save(user);
    }

    @Transactional
    public void realtorSignUp(SignUpRealtorRequestDto signUpRealtorRequestDto) {
        validateRealtorSignUpInfo(signUpRealtorRequestDto);
        signUpRealtorRequestDto.setPassword(passwordEncoder.encode(signUpRealtorRequestDto.getPassword()));
        Realtor realtor = new Realtor(signUpRealtorRequestDto);
        userRepository.save(realtor);
    }


/*    @Transactional
    public TokenResponseDto login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(LoginFailureException::new);
        validatePassword(loginRequestDto, user);
        validateCheck(loginRequestDto, user);

        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = loginRequestDto.toAuthentication(user);

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return new TokenResponseDto(tokenDto.getAccessToken(), tokenDto.getRefreshToken());


    }*/


/*    @Transactional
    public TokenResponseDto reissue(TokenRequestDto tokenRequestDto) {
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return new TokenResponseDto(tokenDto.getAccessToken(), tokenDto.getRefreshToken());

    }*/


    private void validateMemberSignUpInfo(SignUpRequestDto signUpRequestDto) {
        if (userRepository.existsByEmail(signUpRequestDto.getEmail()))
            throw new MemberEmailAlreadyExistsException();
    }
    private void validateRealtorSignUpInfo(SignUpRealtorRequestDto signUpRealtorRequestDto) {
        if (userRepository.existsByEmail(signUpRealtorRequestDto.getEmail()))
            throw new MemberEmailAlreadyExistsException();
    }


    private void validatePassword(LoginRequestDto loginRequestDto, User user) {
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new LoginFailureException();
        }
    }

    private void validateCheck(LoginRequestDto loginRequestDto, User user) {
        if (user.getAuthority() == ROLE_REALTOR ){
            Realtor realtor = (Realtor) user;
            if (realtor.getCheck() == 0) {
                throw new RuntimeException("관리자 승인 대기중입니다.");
            } else if (realtor.getCheck() == 2) {
                throw new RuntimeException("관리자 승인이 거부되었습니다.");
            }
    }


    }

}
