package com.SQD20.SQD20.LIVEPROJECT.service.impl;
import org.springframework.mail.MailSendException;
import com.SQD20.SQD20.LIVEPROJECT.infrastructure.exception.EmailNotSentException;


import com.SQD20.SQD20.LIVEPROJECT.payload.request.EmailDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailServiceImplTest {

    private EmailServiceImpl emailService;

    @Mock
    private JavaMailSender javaMailSender;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        emailService = new EmailServiceImpl(javaMailSender);
    }

    @Test
    void sendEmailAlert_Success() {
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient("gbajeeva@yahoo.com");
        emailDetails.setSubject("Test");
        emailDetails.setMessageBody("This is a test");

        assertDoesNotThrow(() -> emailService.sendEmailAlert(emailDetails));

        ArgumentCaptor<SimpleMailMessage> argumentCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(javaMailSender).send(argumentCaptor.capture());
        SimpleMailMessage sentMessage = argumentCaptor.getValue();
        assertEquals("gbajeeva@yahoo.com", Objects.requireNonNull(sentMessage.getTo())[0]);
        assertEquals("Test", sentMessage.getSubject());
        assertEquals("This is a test", sentMessage.getText());
    }

    @Test
    void sendEmailAlert_Failure() {
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient("gbajeeva@yahoo.com");
        emailDetails.setSubject("Test");
        emailDetails.setMessageBody("This is a test");

        // Mock JavaMailSender to throw a EmailNotSentException when send is called
        doThrow(new EmailNotSentException("Mail not sent")) // Customize the exception message as needed
                .when(javaMailSender)
                .send(any(SimpleMailMessage.class));

        assertThrows(EmailNotSentException.class, () -> emailService.sendEmailAlert(emailDetails));
    }
}