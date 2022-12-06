package com.example.backend.global.config.jwt;

import com.example.backend.global.security.auth.UserDetailsImpl;
import com.example.backend.user.exception.user.*;
import com.example.backend.user.model.AccountCheck;
import com.example.backend.user.model.Authority;
import com.example.backend.user.model.Realtor;
import com.example.backend.user.model.User;
import com.example.backend.user.dto.request.LoginRequestDto;
import com.example.backend.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private ObjectMapper objectMapper = new ObjectMapper();
    private JWTUtil jwt = new JWTUtil();
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public JWTLoginFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        setFilterProcessesUrl("/v1/login");
        this.userRepository = userRepository;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {

        String refreshToken = request.getHeader("refresh-token");

        if (refreshToken == null) {
            LoginRequestDto userLogin = objectMapper.readValue(request.getInputStream(), LoginRequestDto.class);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    userLogin.getEmail(), userLogin.getPassword(), null);
            return getAuthenticationManager().authenticate(token);

        } else {
            VerifyResult verify = JWTUtil.verify(refreshToken);

            if (verify.isSuccess()) {
                User user = userRepository.findByEmail(verify.getUserId())
                        .orElseThrow(MemberNotFoundException::new);
                return new UsernamePasswordAuthenticationToken(
                        new UserDetailsImpl(user), user.getPassword());

            } else {
                throw new TokenExpiredException();
            }
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();

        User user = userDetails.getUser();
        if(user.getAuthority() == Authority.ROLE_REALTOR){
            Realtor realtor = (Realtor) user;
            if(realtor.getAccountCheck() == AccountCheck.APPROVE_WAIT) {
                throw new RealtorNotApprovedYetException();
            } else if (realtor.getAccountCheck() == AccountCheck.APPROVE_REJECT) {
                throw new RealtorNotApprovedException();
            }
        }

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String refreshToken = jwt.makeRefreshToken(userDetails);

        response.setHeader("refresh_token", refreshToken);
        response.setHeader("access_token", jwt.makeAuthToken(userDetails));
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        response.getOutputStream().write(objectMapper.writeValueAsBytes(new UserResponseDto(userDetails.getUser())));
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed
    ) throws IOException, ServletException {

        SecurityContextHolder.clearContext();
        throw new LoginFailureException();

    }


    public static class UserResponseDto {
        public int accountState;
        public String email;
        public String nickname;

        public UserResponseDto(User user) {
            this.accountState = user.getAuthority().getNum();
            this.email = user.getEmail();
            this.nickname = user.getNickname();
        }
    }

}