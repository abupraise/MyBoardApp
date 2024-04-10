package com.SQD20.SQD20.LIVEPROJECT.utils;

import org.springframework.beans.factory.annotation.Value;

public class EmailTemplate {

    public static String getEmailMessage(String name, String baseUrl, String token) {
        return "Hello " + name + ",\n\nYour new account has been created. Please click the link below to verify your account. \n\n" +
                getVerificationUrl(baseUrl, token) + "\n\nThe support Team";
    }

    private static String getVerificationUrl( String baseUrl, String token) {
        return baseUrl + "/api/v1/auth/verify-email?token=" + token;
    }
}
