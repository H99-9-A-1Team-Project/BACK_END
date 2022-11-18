package com.example.backend.consult.service;

import com.example.backend.consult.dto.UserAllConsultResponseDto;
import com.example.backend.footsteps.repository.FootstepsRepository;
import com.example.backend.global.config.auth.UserDetailsImpl;
import com.example.backend.global.entity.AnswerState;
import com.example.backend.global.entity.Consult;
import com.example.backend.consult.dto.RegisterConsultDto;
import com.example.backend.consult.repository.ConsultRepository;
import com.example.backend.global.entity.FootstepsPost;
import com.example.backend.global.exception.customexception.common.AccessDeniedException;
import com.example.backend.global.exception.customexception.user.UserUnauthorizedException;
import com.example.backend.user.repository.RealtorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConsultService {

    private final ConsultRepository consultRepository;
    private final RealtorRepository realtorRepository;

    private final FootstepsRepository footstepsRepository;

    @Transactional
    public void registerConsult(UserDetailsImpl userDetails, RegisterConsultDto dto) {
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
                .answerState(AnswerState.ROLE_WAIT)
                .build();
        consultRepository.save(consult);
    }

    public List<UserAllConsultResponseDto> allConsult(Long id) {
        List<Consult> consultList = consultRepository.findAllByUserId(id);
        List<UserAllConsultResponseDto> userAllConsultResponseDtoList = consultList.stream()
                .map(consult -> new UserAllConsultResponseDto(consult))
                .collect(Collectors.toList());
        return userAllConsultResponseDtoList;
    }


    public List<UserAllConsultResponseDto> waitConsult(UserDetailsImpl userDetails) {
        validRealtor(userDetails);
        List<Consult> consultList = consultRepository.findAllByAnswerState(AnswerState.ROLE_WAIT.ordinal());
        List<UserAllConsultResponseDto> userAllConsultResponseDtoList = consultList.stream()
                .map(consult -> new UserAllConsultResponseDto(consult))
                .collect(Collectors.toList());
        return userAllConsultResponseDtoList;

    }

    public void validAuth(UserDetailsImpl userDetails){
        if(userDetails == null) throw new UserUnauthorizedException();
    }
    private void validRealtor(UserDetailsImpl userDetails){
        realtorRepository.findByEmail(userDetails.getUser().getEmail())
                .orElseThrow(AccessDeniedException::new);
    }


}