package com.SQD20.SQD20.LIVEPROJECT.service;

import com.SQD20.SQD20.LIVEPROJECT.payload.request.TaskListRequest;
import com.SQD20.SQD20.LIVEPROJECT.payload.request.TaskRequest;

public interface TaskListService {
    public void updateTaskList(Long taskId, TaskListRequest taskListRequest);

}
