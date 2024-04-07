package com.SQD20.SQD20.LIVEPROJECT.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails {

    private String recipient;
    private String subject;
    private String messageBody;
    private String attachment;

}
