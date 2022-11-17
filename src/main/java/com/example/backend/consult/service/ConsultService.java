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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConsultService {

    private final ConsultRepository consultRepository;

    @Transactional
    public ResponseEntity<?> registerConsult(UserDetailsImpl userDetails, RegisterConsultDto dto){

        Consult consult = Consult.builder()
                .title(dto.getTitle())
                .coordX(dto.getCoordX())
                .coordY(dto.getCoordY())
                .check1(trueCheck(dto.getCheck1()))
                .check2(trueCheck(dto.getCheck2()))
                .check3(trueCheck(dto.getCheck3()))
                .check4(trueCheck(dto.getCheck4()))
                .check5(trueCheck(dto.getCheck5()))
                .check6(trueCheck(dto.getCheck6()))
                .consultMessage(dto.getConsultMessage())
                .answerState(AnswerState.ROLE_WAIT)
                .build();
        consultRepository.save(consult);
        return ResponseEntity.ok()
                .build();
    }

    public int trueCheck(boolean bool){
        if(bool){
            return 1;
        }else{
            return 0;
        }
    }
}
