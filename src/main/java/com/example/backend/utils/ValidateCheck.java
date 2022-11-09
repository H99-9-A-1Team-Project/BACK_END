package com.example.backend.utils;

import com.example.backend.domain.Member;
import com.example.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ValidateCheck {

    private final MemberRepository memberRepository;

    public Member isPresentMember(String username){
        Optional<Member> optionalMember = memberRepository.findByUsername(username);
        return optionalMember.orElse(null);
    }
}
