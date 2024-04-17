package com.SQD20.SQD20.LIVEPROJECT.service;

import com.SQD20.SQD20.LIVEPROJECT.domain.entites.TaskList;
import com.SQD20.SQD20.LIVEPROJECT.payload.request.TaskListRequest;
import com.SQD20.SQD20.LIVEPROJECT.payload.request.TaskRequest;
import com.SQD20.SQD20.LIVEPROJECT.payload.response.TaskResponse;

public interface TaskListService {
    public TaskList updateTaskListByUserId(Long taskListId, TaskListRequest taskListRequest);
    TaskResponse createTaskList(Long id, TaskListRequest request);

    void deleteTaskListByTitle(String title);

    void deleteTaskListById(long Id);

}
