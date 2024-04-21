package com.SQD20.SQD20.LIVEPROJECT.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskListResponse {
    private String responseCode;
    private String responseMessage;
    private String title;
    private String description;
}
