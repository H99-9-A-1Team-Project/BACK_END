package com.example.backend.global.security.auth;

import com.example.backend.user.model.Authority;
import com.example.backend.user.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.Map;

// OAuth2User, UserDetails 두 인터페이스 상속
// JWT와 OAuth client 두 개의 principal 객체를 만들지 않고 한 번에 처리할 수 있도록 함.
@Data
public class UserDetailsImpl implements UserDetails {

    private final User user;

    private Map<String, Object> attributes;

    private Authority authority;

    // 일반 로그인
    public UserDetailsImpl(User user) {
        this.user = user;
        this.authority = user.getAuthority();
    }

    // OAuth 로그인
    // attributes 데이터를 토대로 내가 user 객체를 만들 예정.
    public UserDetailsImpl(User user, Map<String,Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    public User getUser() {
        return user;
    }


    public Authority getAuthority() {
        return user.getAuthority();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }


}