package com.SQD20.SQD20.LIVEPROJECT.payload.request;

import com.SQD20.SQD20.LIVEPROJECT.domain.enums.PriorityLevel;
import com.SQD20.SQD20.LIVEPROJECT.domain.enums.Status;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime deadline;
    private PriorityLevel priorityLevel;
    private Status status;
}
