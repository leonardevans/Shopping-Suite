package com.shoppingsuite.security.oauth2.user;

import com.shoppingsuite.security.oauth2.user.OAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        return new GoogleOAuth2UserInfo(attributes);
    }
}
