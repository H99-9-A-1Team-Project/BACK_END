package com.example.backend.user.service;


import com.example.backend.global.infra.S3.service.AmazonS3Service;
import com.example.backend.global.security.auth.UserDetailsImpl;
import com.example.backend.user.dto.request.RealtorApproveRequestDto;
import com.example.backend.user.dto.request.RealtorEditRequestDto;
import com.example.backend.user.dto.response.RealtorListResponseDto;
import com.example.backend.user.model.AccountCheck;
import com.example.backend.user.model.Authority;
import com.example.backend.user.model.Realtor;
import com.example.backend.global.exception.customexception.AccessDeniedException;
import com.example.backend.user.exception.user.MemberNotFoundException;
import com.example.backend.user.exception.user.UserUnauthorizedException;
import com.example.backend.global.util.mail.MailDto;
import com.example.backend.global.util.mail.MailService;
import com.example.backend.user.repository.RealtorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RealtorService {

    private final RealtorRepository realtorRepository;
    private final AmazonS3Service amazonS3Service;
    private final MailService mailService;
    private final SpringTemplateEngine templateEngine;


    @Transactional
    public void approveRealtor(RealtorApproveRequestDto dto, UserDetailsImpl userDetails) throws MessagingException {
        validateManager(userDetails);

        Realtor realtor = realtorRepository.findByEmail(dto.getEmail()).orElseThrow(MemberNotFoundException::new);
        realtor.update(dto);

        sendApproveResultEmail(dto, realtor);
    }

    private void sendApproveResultEmail(RealtorApproveRequestDto dto, Realtor realtor) throws MessagingException {
        String htmlTemplate;
        MailDto mailContent = new MailDto(realtor.getEmail());
        Context context = new Context();

        context.setVariable("name", realtor.getEmail());
        AccountCheck accountCheck = dto.getAccountCheck();

        htmlTemplate = getMailTemplateByAccountCheck(mailContent, context, accountCheck);
        mailService.sendSimpleMessage(mailContent, htmlTemplate);
    }

    private String getMailTemplateByAccountCheck(MailDto mail, Context context, AccountCheck accountCheck) {
        if(accountCheck == AccountCheck.APPROVE_COMPLETE) {
            return getApproveMailTemplate(mail, context);
        }
        return getRejectMailTemplate(mail, context);
    }

    private String getRejectMailTemplate(MailDto mail, Context context) {
        mail.setRealtorRejectMessage();
        return templateEngine.process("reject-mail-template", context);
    }

    private String getApproveMailTemplate(MailDto mail, Context context) {
        mail.setRealtorApproveMessage();
        return templateEngine.process("approve-mail-template", context);
    }

    @Transactional(readOnly = true)
    public List<RealtorListResponseDto> getRealtorList(UserDetailsImpl userDetails) {
        validateManager(userDetails);

        List<Realtor> realtorList = realtorRepository.findAll();
        return realtorList.stream().map(RealtorListResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public void editRealtorProfile(MultipartFile multipartFile, RealtorEditRequestDto realtorEditRequestDto, UserDetailsImpl userDetails) throws IOException {
        validRealtor(userDetails);

        Realtor realtor = realtorRepository.findByEmail(userDetails.getUser().getEmail()).orElseThrow();
        if (multipartFile == null || multipartFile.isEmpty()) {
            realtor.update(realtorEditRequestDto);
            return;
        }

        String imageUrl = amazonS3Service.upload(multipartFile, "realtor-authentication", userDetails.getUser().getEmail());
        realtor.update(realtorEditRequestDto, imageUrl);
    }


    private void validAuth(UserDetailsImpl userDetails){
        if(userDetails == null)
            throw new UserUnauthorizedException();
    }

    private void validRealtor(UserDetailsImpl userDetails){
        if(userDetails.getAuthority() != Authority.ROLE_REALTOR)
            throw new AccessDeniedException();
    }

    private void validateManager(UserDetailsImpl userDetails) {
        validAuth(userDetails);
        if (userDetails.getAuthority() != Authority.ROLE_ADMIN)
            throw new AccessDeniedException();
    }
}


