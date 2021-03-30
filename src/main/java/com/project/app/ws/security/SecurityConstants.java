package com.project.app.ws.security;

import com.project.app.ws.SpringApplicationContext;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 846000000;    // 10 Days
    public static final long PASSWORD_RESET_EXPIRATION_TIME = 3600000;  // 1 Day
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users";
    public static final String VERIFICATION_EMAIL_URL = "/users/email-verification";
    public static final String PASSWORD_RESET_REQUEST_URL = "/users/password-reset-request";
    public static final String PASSWORD_RESET_URL = "/users/password-reset";
    public static final String PRODUCT_URL = "/product";
    public static final String CATEGORY_URL = "/category";
    public static final String ORDER_URL = "/order";
    public static final String CART_URL = "/order/cart";

    public static String getSecurityToken() {
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return appProperties.getTokenSecret();
    }
}
