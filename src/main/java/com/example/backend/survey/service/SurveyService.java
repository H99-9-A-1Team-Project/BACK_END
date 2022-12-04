package com.example.backend.survey.service;

import com.example.backend.global.exception.customexception.AccessDeniedException;
import com.example.backend.global.security.auth.UserDetailsImpl;
import com.example.backend.survey.repository.SurveyRepository;
import com.example.backend.user.exception.user.UserUnauthorizedException;
import com.example.backend.user.model.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;

    public Object getUserSurvey(UserDetailsImpl userDetails, Pageable pageable) {
        validAdmin(userDetails);
        return surveyRepository.findAll(pageable).getContent();

    }

    public void validAuth(UserDetailsImpl userDetails){
        if(userDetails == null) throw new UserUnauthorizedException();
    }

    private void validAdmin(UserDetailsImpl userDetails){
        validAuth(userDetails);
        if(userDetails.getAuthority() != Authority.ROLE_ADMIN)
            throw new AccessDeniedException();
    }
}
