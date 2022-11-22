package com.example.backend.consult.service;

import com.example.backend.comment.repository.CommentRepository;
import com.example.backend.consult.dto.RepliedConsultResponseDto;
import com.example.backend.comment.dto.CommentResponseDto;
import com.example.backend.consult.dto.DetailConsultResponseDto;
import com.example.backend.consult.dto.UserAllConsultResponseDto;
import com.example.backend.global.config.auth.UserDetailsImpl;
import com.example.backend.global.entity.*;
import com.example.backend.consult.dto.RegisterConsultDto;
import com.example.backend.consult.repository.ConsultRepository;
import com.example.backend.global.exception.customexception.common.AccessDeniedException;
import com.example.backend.global.exception.customexception.user.MemberNotFoundException;
import com.example.backend.global.exception.customexception.user.UserUnauthorizedException;
import com.example.backend.user.repository.RealtorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConsultService {

    private final ConsultRepository consultRepository;
    private final CommentRepository commentRepository;
    private final RealtorRepository realtorRepository;

    @Transactional
    public void registerConsult(UserDetailsImpl userDetails, RegisterConsultDto dto) {
        validAuth(userDetails);
        Consult consult = Consult.builder()
                .title(dto.getTitle())
                .coordX(dto.getCoordX())
                .coordY(dto.getCoordY())
                .check1(dto.isCheck1())
                .check2(dto.isCheck2())
                .check3(dto.isCheck3())
                .check4(dto.isCheck4())
                .check5(dto.isCheck5())
                .check6(dto.isCheck6())
                .consultMessage(dto.getConsultMessage())
                .createDate(LocalDateTime.now())
                .user(userDetails.getUser())
                .answerState(AnswerState.WAIT)
                .build();
        consultRepository.save(consult);
    }

    public List<UserAllConsultResponseDto> allConsult(Long id) {
        List<Consult> consultList = consultRepository.findAllByUserId(id);
        return consultList.stream()
                .map(UserAllConsultResponseDto::new)
                .collect(Collectors.toList());
    }


    public List<UserAllConsultResponseDto> waitConsult(UserDetailsImpl userDetails) {
        validRealtor(userDetails);
        List<Consult> consultList = consultRepository.findAllByAnswerState(AnswerState.WAIT.ordinal());
        return consultList.stream()
                .map(UserAllConsultResponseDto::new)
                .collect(Collectors.toList());

    }
    public DetailConsultResponseDto detailConsult(Long consultId, UserDetailsImpl userDetails) {
        validAuth(userDetails);
        Consult consult = consultRepository.findById(consultId).orElseThrow();
        List<Comment> commentList = commentRepository.findAllById(consultId);
        List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
        for(Comment comment: commentList){
        commentResponseDtos.add(
                CommentResponseDto.builder()
                        .nickname(comment.getRealtor().getNickname())
                        .profile(comment.getRealtor().getProfile())
                        .introMessage(comment.getRealtor().getIntroMessage())
                        .createdAt(comment.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                        .answerMessage(comment.getContent())
                        .build()
        );
        }
        return DetailConsultResponseDto.builder()
                        .Id(consult.getId())
                        .title(consult.getTitle())
                        .coordX(consult.getCoordX())
                        .coordY(consult.getCoordY())
                        .answerState(consult.getAnswerState())
                        .check1(consult.isCheck1())
                        .check2(consult.isCheck2())
                        .check3(consult.isCheck3())
                        .check4(consult.isCheck4())
                        .check5(consult.isCheck5())
                        .check6(consult.isCheck6())
                        .comments(commentResponseDtos)
                        .createdAt(consult.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                        .build();


    }

    public List<RepliedConsultResponseDto> getRepliedConsult(UserDetailsImpl userDetails) {
        validRealtor(userDetails);
        List<RepliedConsultResponseDto> result = new ArrayList<>();
        Realtor realtor = realtorRepository.findByEmail(userDetails.getUser().getEmail()).orElseThrow(MemberNotFoundException::new);

        commentRepository.findByRealtor(realtor)
                .forEach(comment -> { if(comment.getConsult().getAnswerState() == AnswerState.ANSWER ){
                    result.add(new RepliedConsultResponseDto(comment.getConsult(), comment.getContent()));
                }});

        return result;
    }
//    public DetailConsultResponseDto PutdetailConsult(Long consult_id, UserDetailsImpl userDetails) {
//    }

    public void validAuth(UserDetailsImpl userDetails){
        if(userDetails == null) throw new UserUnauthorizedException();
    }

    private void validRealtor(UserDetailsImpl userDetails){
        validAuth(userDetails);
        if(userDetails.getAuthority() != Authority.ROLE_REALTOR)
            throw new AccessDeniedException();
    }
    private void validUser(UserDetailsImpl userDetails){
        validAuth(userDetails);
        if(userDetails.getAuthority() != Authority.ROLE_USER)
            throw new AccessDeniedException();
    }



}