package com.project.app.ws.security;

import com.project.app.ws.SpringApplicationContext;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 846000000;    // 10 Days
    public static final long PASSWORD_RESET_EXPIRATION_TIME = 3600000;  // 1 Day
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_ENDPOINT = "/users";
    public static final String VERIFICATION_EMAIL_ENDPOINT = "/users/email-verification";
    public static final String PASSWORD_RESET_REQUEST_ENDPOINT = "/users/password-reset-request";
    public static final String PASSWORD_RESET_ENDPOINT = "/users/password-reset";
    public static final String PRODUCT_ENDPOINT = "/product*/**";
    public static final String CATEGORY_ENDPOINT = "/category/**";
    public static final String ORDER_ENDPOINT = "/users/*/order/**";
    public static final String INVOICE_ENDPOINT = "/users/*/invoices/**";
    public static final String INVOICES_ENDPOINT = "/invoices/**";

    public static String getSecurityToken() {
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return appProperties.getTokenSecret();
    }
}
