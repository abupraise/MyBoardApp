package com.SQD20.SQD20.LIVEPROJECT.payload.request;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailDetails {

    private String recipient;
    private String subject;
    private String messageBody;
    private String attachment;

}
