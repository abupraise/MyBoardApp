package com.SQD20.SQD20.LIVEPROJECT.service;

import com.SQD20.SQD20.LIVEPROJECT.domain.entites.Task;
import com.SQD20.SQD20.LIVEPROJECT.payload.request.TaskRequest;


public interface TaskService {

    public void deleteById(Long id);
    public void updateTask(Long taskId, TaskRequest updateRequest);
}
