package com.example.backend.global.config.oauth;

import com.example.backend.global.config.oauth.provider.KakaoUserInfo;
import com.example.backend.global.config.oauth.provider.OAuth2UserInfo;
import com.example.backend.global.security.auth.UserDetailsImpl;
import com.example.backend.user.model.Authority;
import com.example.backend.user.model.Member;
import com.example.backend.user.model.User;
import com.example.backend.user.repository.MemberRepository;
import com.example.backend.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private BCryptPasswordEncoder bcryptPasswordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        OAuth2UserInfo oauth2UserInfo = null;

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        oauth2UserInfo = getUserInfo(oauth2User, oauth2UserInfo, registrationId);
        User user = registerUser(userRequest, oauth2UserInfo);

        return new UserDetailsImpl(user, oauth2User.getAttributes());
    }

    private static OAuth2UserInfo getUserInfo(OAuth2User oauth2User, OAuth2UserInfo oauth2UserInfo, String registrationId) {
        if (registrationId.equals("kakao")) {
            oauth2UserInfo = new KakaoUserInfo(oauth2User.getAttributes());
        } else {
            throw new RuntimeException("유효하지 않은 provider 입니다.");
        }

        return oauth2UserInfo;
    }

    private User registerUser(OAuth2UserRequest userRequest, OAuth2UserInfo oauth2UserInfo) {
        String provider = oauth2UserInfo.getProvider();
        String providerId = oauth2UserInfo.getProviderId();
        String nickname = oauth2UserInfo.getName();
        String userId = provider + "_" + providerId;
        String password = bcryptPasswordEncoder.encode(userRequest.getClientRegistration().getClientSecret());

        Member member = memberRepository.findByEmail(userId);

        if (member == null) {
            member = Member.builder()
                    .email(userId)
                    .password(password)
                    .provider(provider)
                    .providerId(providerId)
                    .authority(Authority.ROLE_USER)
                    .nickname(nickname)
                    .profileImg(1)
                    .build();

            memberRepository.save(member);
        }

        return member;
    }
}
