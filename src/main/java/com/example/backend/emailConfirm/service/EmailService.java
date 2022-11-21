package com.example.backend.emailConfirm.service;

import com.example.backend.emailConfirm.dto.EmailConfirmRequestDto;
import com.example.backend.global.exception.customexception.register.MemberEmailAlreadyExistsException;
import com.example.backend.global.response.Response;
import com.example.backend.user.repository.RealtorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmailService {

    private final RealtorRepository realtorRepository;

    @Transactional
    public Response emailConfirm(EmailConfirmRequestDto emailConfirmRequestDto){
        if(realtorRepository.findByEmail(emailConfirmRequestDto.getEmail()).isEmpty()){
            return Response.success();
        }else{
            throw new MemberEmailAlreadyExistsException();
        }

    }

}
