package com.SQD20.SQD20.LIVEPROJECT.service.impl;

import com.SQD20.SQD20.LIVEPROJECT.payload.request.TaskListRequest;
import com.SQD20.SQD20.LIVEPROJECT.service.TaskListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskListServiceImpl implements TaskListService {

    @Override
    public void updateTaskList(Long taskId, TaskListRequest taskListRequest) {

    }
}
