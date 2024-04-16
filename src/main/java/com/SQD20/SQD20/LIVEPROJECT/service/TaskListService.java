package com.SQD20.SQD20.LIVEPROJECT.service;

import com.SQD20.SQD20.LIVEPROJECT.domain.entites.TaskList;
import com.SQD20.SQD20.LIVEPROJECT.payload.request.TaskListRequest;
import com.SQD20.SQD20.LIVEPROJECT.payload.request.TaskRequest;

public interface TaskListService {
    public TaskList updateTaskListByUserId(Long taskListId, TaskListRequest taskListRequest);

}
