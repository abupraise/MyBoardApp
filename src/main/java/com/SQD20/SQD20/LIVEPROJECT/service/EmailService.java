package com.SQD20.SQD20.LIVEPROJECT.service;

import com.SQD20.SQD20.LIVEPROJECT.payload.request.EmailDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.mail.MessagingException;

public interface EmailService {

    void sendHtmlMessageForResetPassword(EmailDetails message, String name, String link) throws MessagingException, JsonProcessingException;

    void sendEmailAlert(EmailDetails emailDetails);

    void sendEmailWithAttachment(EmailDetails emailDetails);

    public void sendSimpleMailMessage(String name, String to, String token);

}
