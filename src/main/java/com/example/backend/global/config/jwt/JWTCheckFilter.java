package com.example.backend.global.config.jwt;

import com.example.backend.global.security.auth.UserDetailsImpl;
import com.example.backend.global.security.auth.UserDetailsServiceImpl;
import com.example.backend.user.exception.user.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JWTCheckFilter extends BasicAuthenticationFilter {

    private UserDetailsServiceImpl userDetailsServiceImpl;
    private ObjectMapper objectMapper = new ObjectMapper();

    public JWTCheckFilter(AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsServiceImpl) {
        super(authenticationManager);
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String bearer = request.getHeader("access-token");

       if(bearer == null || !bearer.startsWith("Bearer ")){
            chain.doFilter(request,response);
            return;
        }
       String token = bearer.substring("Bearer ".length());

       VerifyResult result = JWTUtil.verify(token);

       if(result.isSuccess()){
           UserDetailsImpl userDetailsImpl = (UserDetailsImpl) userDetailsServiceImpl.loadUserByUsername(result.getUserId());

           UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(
                   userDetailsImpl, null, userDetailsImpl.getAuthorities()
           );

           SecurityContextHolder.getContext().setAuthentication(userToken);
           chain.doFilter(request,response);
       } else {
           throw new TokenExpiredException();
       }

    }
}
