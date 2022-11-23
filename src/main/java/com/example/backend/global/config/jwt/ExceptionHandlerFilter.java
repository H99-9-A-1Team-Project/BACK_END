package com.example.backend.global.config.jwt;

import com.example.backend.global.exception.CustomException;
import com.example.backend.global.exception.ErrorCode;
import com.example.backend.global.exception.ErrorResponse;
import com.example.backend.global.exception.customexception.user.MemberNotFoundException;
import com.example.backend.global.exception.customexception.user.RealtorNotApprovedException;
import com.example.backend.global.exception.customexception.user.RealtorNotApprovedYetException;
import com.example.backend.global.exception.customexception.user.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.backend.global.exception.ErrorCode.*;

@Slf4j
@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request,response);
        } catch (RealtorNotApprovedYetException ex){
            setErrorResponse(REALTOR_NOT_APPROVED_YET, response,ex);
        } catch (RealtorNotApprovedException ex){
            setErrorResponse(REALTOR_NOT_APPROVED, response,ex);
        }  catch (TokenExpiredException ex) {
            setErrorResponse(TOKEN_EXPIRED_EXCEPTION, response,ex);
        } catch (MemberNotFoundException | UsernameNotFoundException ex) {
            setErrorResponse(MEMBER_NOT_FOUND, response, ex);
        } catch (IOException ex) {
            setErrorResponse(INTERNAL_SERVER_ERROR, response, ex);
        }

    }

    public void setErrorResponse(ErrorCode code, HttpServletResponse response,Throwable ex){
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(code.getStatus().value());
        response.setContentType("application/json");
        ErrorResponse errorResponse = new ErrorResponse(code);
        try{
            response.getOutputStream().write(objectMapper.writeValueAsBytes(errorResponse));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}