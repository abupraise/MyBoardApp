package com.SQD20.SQD20.LIVEPROJECT.service;

import com.SQD20.SQD20.LIVEPROJECT.payload.request.EmailDetails;

public interface EmailService {

    void sendEmailAlert(EmailDetails emailDetails);

    void sendEmailWithAttachment(EmailDetails emailDetails);

}
