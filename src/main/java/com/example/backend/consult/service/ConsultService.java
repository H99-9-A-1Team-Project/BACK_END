package com.example.backend.consult.service;

import com.example.backend.global.config.auth.UserDetailsImpl;
import com.example.backend.global.entity.AnswerState;
import com.example.backend.global.entity.Consult;
import com.example.backend.consult.dto.RegisterConsultDto;
import com.example.backend.consult.repository.ConsultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConsultService {

    private final ConsultRepository consultRepository;

    @Transactional
    public void registerConsult(UserDetailsImpl userDetails, RegisterConsultDto dto){
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
}
