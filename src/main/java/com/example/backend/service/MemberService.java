package com.example.backend.service;

import com.example.backend.Exception.ErrorCode;
import com.example.backend.Exception.GlobalException;
import com.example.backend.domain.Member;
import com.example.backend.domain.RefreshToken;
import com.example.backend.dto.ResponseDto;
import com.example.backend.dto.request.LoginRequestDto;
import com.example.backend.dto.request.MemberRequestDto;
import com.example.backend.dto.response.LoginResponseDto;
import com.example.backend.dto.response.MemberResponseDto;
import com.example.backend.repository.MemberRepository;
import com.example.backend.repository.RefreshTokenRepository;
import com.example.backend.security.jwt.JwtUtil;
import com.example.backend.security.jwt.TokenDto;
import com.example.backend.utils.ValidateCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ValidateCheck validateCheck;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    //회원가입
    @Transactional
    public ResponseEntity<ResponseDto<MemberResponseDto>> signup(MemberRequestDto memberReqDto) {

        // username 중복 검사
        usernameDuplicateCheck(memberReqDto);

        // 비빌번호 확인 & 비빌번호 불일치
        if(!memberReqDto.getPassword().equals(memberReqDto.getPasswordConfirm())){
            throw new GlobalException(ErrorCode.BAD_PASSWORD_CONFIRM);
        }

        Member member = Member.builder()
                .username(memberReqDto.getUsername())
                .password(passwordEncoder.encode(memberReqDto.getPassword()))
                .profileImg("https://mykeejaebucket.s3.ap-northeast-2.amazonaws.com/Pictures/9295961a-4020-4c94-8c49-3bd7ef0e62ac.png")
                .build();
        memberRepository.save(member);
        return ResponseEntity.ok().body(ResponseDto.success(
                MemberResponseDto.builder()
                        .username(member.getUsername())
                        .profileImg(member.getProfileImg())
                        .createdAt(member.getCreatedAt())
                        .modifiedAt(member.getModifiedAt())
                        .build()
        ));

    }

    public void usernameDuplicateCheck(MemberRequestDto memberReqDto) {
        if(memberRepository.findByUsername(memberReqDto.getUsername()).isPresent()){
            throw new GlobalException(ErrorCode.DUPLICATE_MEMBER_ID);
            // ex) return ResponseDto.fail()
        }
    }

    //로그인
    @Transactional
    public ResponseEntity<ResponseDto<LoginResponseDto>> login(LoginRequestDto loginReqDto, HttpServletResponse response) {

        Member member = validateCheck.isPresentMember(loginReqDto.getUsername());

        //사용자가 있는지 확인
        if(null == member){
            throw new GlobalException(ErrorCode.MEMBER_NOT_FOUND);
        }
        //비밀번호가 맞는지 확인
        if(!member.validatePassword(passwordEncoder, loginReqDto.getPassword())){
            throw new GlobalException(ErrorCode.BAD_PASSWORD);
        }

        TokenDto tokenDto = jwtUtil.createAllToken(loginReqDto.getUsername());

        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByMemberUsername(loginReqDto.getUsername());

        if(refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        }else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), loginReqDto.getUsername());
            refreshTokenRepository.save(newToken);
        }
        setHeader(response, tokenDto);

        return ResponseEntity.ok().body(ResponseDto.success(
                LoginResponseDto.builder()
                        .username(member.getUsername())
                        .createdAt(member.getCreatedAt())
                        .modifiedAt(member.getModifiedAt())
                        .build()
        ));
    }
    private void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());

    }
}
