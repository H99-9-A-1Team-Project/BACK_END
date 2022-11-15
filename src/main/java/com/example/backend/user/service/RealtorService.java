package com.example.backend.user.service;


import com.example.backend.global.config.auth.UserDetailsImpl;
import com.example.backend.global.entity.Authority;
import com.example.backend.global.entity.Realtor;
import com.example.backend.global.entity.User;
import com.example.backend.global.exception.customexception.common.AccessDeniedException;
import com.example.backend.global.exception.customexception.user.MemberNotEqualsException;
import com.example.backend.global.exception.customexception.user.MemberNotFoundException;
import com.example.backend.global.exception.customexception.user.UserUnauthorizedException;
import com.example.backend.user.dto.*;
import com.example.backend.user.repository.RealtorRepository;
import com.example.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RealtorService {

    private final UserRepository userRepository;
    private final RealtorRepository realtorRepository;

    @Transactional
    public void approveRealtor(RealtorApproveDto dto, UserDetailsImpl userDetails) {
        validateManager(userDetails);
        Realtor realtor = realtorRepository.findByEmail(dto.getEmail())
                .orElseThrow(MemberNotFoundException::new);
        realtor.update(dto);
    }

    @Transactional(readOnly = true)
    public List<RealtorListResponseDto> getRealtorApprovalList(UserDetailsImpl userDetails) {
        validateManager(userDetails);
        List<Realtor> realtorList = realtorRepository.findByCheck(1L);
        return realtorList.stream().map(RealtorListResponseDto::new).collect(Collectors.toList());
    }

    private void validateManager(UserDetailsImpl userDetails) {
        if(userDetails == null){ throw new UserUnauthorizedException(); }
        User manager = userRepository.findByEmail(userDetails.getUser().getEmail())
                .orElseThrow(AccessDeniedException::new);
        if(!manager.getAuthority().equals(Authority.ROLE_ADMIN)){
            throw new AccessDeniedException();
        }
    }
}


