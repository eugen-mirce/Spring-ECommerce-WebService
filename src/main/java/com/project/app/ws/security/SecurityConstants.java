package com.project.app.ws.security;

import com.project.app.SpringApplicationContext;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 846000000;    // 10 Days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users";

    public static String getSecurityToken() {
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return appProperties.getTokenSecret();
    }
}
