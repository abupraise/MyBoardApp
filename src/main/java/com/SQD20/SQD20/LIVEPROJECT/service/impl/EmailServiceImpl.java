package com.SQD20.SQD20.LIVEPROJECT.service.impl;

import com.SQD20.SQD20.LIVEPROJECT.infrastructure.exception.EmailNotSentException;
import com.SQD20.SQD20.LIVEPROJECT.payload.request.EmailDetails;
import com.SQD20.SQD20.LIVEPROJECT.service.EmailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine tEngine;
    @Value("${spring.mail.username}")
    private String senderMail;
    private static final String TEMPLATE_PATH = "templates/html/";

    @Override
    public void sendEmailAlert(EmailDetails emailDetails) {

        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(senderMail);
            simpleMailMessage.setTo(emailDetails.getRecipient());
            simpleMailMessage.setSubject(emailDetails.getSubject());
            simpleMailMessage.setText(emailDetails.getMessageBody());

            javaMailSender.send(simpleMailMessage);

            System.out.println("Mail sent successfully");
        } catch (MailException e) {
            throw new EmailNotSentException("Mail not sent");
        }

    }

    @Override
    public void sendEmailWithAttachment(EmailDetails emailDetails) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper;

        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(senderMail);
            mimeMessageHelper.setTo(emailDetails.getRecipient());
            mimeMessageHelper.setSubject(emailDetails.getSubject());
            mimeMessageHelper.setText(emailDetails.getMessageBody());

            FileSystemResource file = new FileSystemResource(new File(emailDetails.getAttachment()));
            mimeMessageHelper.addAttachment(Objects.requireNonNull(file.getFilename()), file);

            javaMailSender.send(mimeMessage);

            log.info(file.getFilename() + " Has been sent successfully to " + emailDetails.getRecipient());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void sendSimpleMailMessage(String name, String to, String token) {

    }
    @Override
    public void sendHtmlMessageForResetPassword(EmailDetails message, String name, String link) throws MessagingException, JsonProcessingException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        Context context = new Context();
        Map<String, Object> variables = Map.of(
                "name", name,
                "link", link
        );
        context.setVariables(variables);
        helper.setFrom(senderMail);
        helper.setTo(message.getRecipient());
        helper.setSubject(message.getSubject());
        String html = tEngine.process("reset-password", context);
        helper.setText(html, true);

        javaMailSender.send(msg);
        log.info("Sending email: to {}",message.getRecipient());
    }

    @Override
    public void sendHtmlMessageToVerifyEmail(EmailDetails message, String name, String link) throws MessagingException, JsonProcessingException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        Context context = new Context();
        Map<String, Object> variables = Map.of(
                "name", name,
                "link", link
        );
        context.setVariables(variables);
        helper.setFrom(senderMail);
        helper.setTo(message.getRecipient());
        helper.setSubject(message.getSubject());
        String html = tEngine.process("email-verification", context);
        helper.setText(html, true);

        javaMailSender.send(msg);
        log.info("Sending email: to {}",message.getRecipient());
    }



}
