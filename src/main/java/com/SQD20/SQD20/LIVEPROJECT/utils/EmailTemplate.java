package com.SQD20.SQD20.LIVEPROJECT.utils;

import org.springframework.beans.factory.annotation.Value;

public class EmailTemplate {

    public static String getEmailMessage(String name, String baseUrl, String token) {
        return "Hello " + name + ",\n\nYour new account has been created. Please click the link below to verify your account. \n\n" +
                getVerificationUrl(baseUrl, token) + "\n\nThe support Team";
    }

    public static String getVerificationUrl( String baseUrl, String token) {
        return baseUrl + "/api/v1/auth/verify-email?token=" + token;
    }

    public static String getForgotPasswordVerificationUrl(String baseUrl, String resetToken) {
        return baseUrl + "/api/v1/auth/verify-forgot-password-email?resetToken=" + resetToken;
    }
}
