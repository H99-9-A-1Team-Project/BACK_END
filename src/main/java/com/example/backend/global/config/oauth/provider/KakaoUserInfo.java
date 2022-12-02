package com.example.backend.global.config.oauth.provider;

import lombok.Data;

import java.util.Map;

@Data
public class KakaoUserInfo implements OAuth2UserInfo{

    private Map<String, Object> attributes;
    private Map<String, Object> properties;


    public KakaoUserInfo(Map<String, Object> attributes){
        this.attributes = attributes;
        this.properties = (Map) attributes.get("properties");

    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getName() {
        return properties.get("nickname").toString();
    }
}
