package com.SQD20.SQD20.LIVEPROJECT.service;

import com.SQD20.SQD20.LIVEPROJECT.domain.entites.Task;
import com.SQD20.SQD20.LIVEPROJECT.domain.enums.Status;
import com.SQD20.SQD20.LIVEPROJECT.payload.request.TaskRequest;
import com.SQD20.SQD20.LIVEPROJECT.payload.response.TasksResponse;

import java.util.List;


public interface TaskService {

    public void deleteTask(Long id);
    public void updateTask(Long taskId, TaskRequest updateRequest);
    public TaskRequest createTask(Long userId, Long taskListId, TaskRequest createRequest);
    public Task updateTaskStatus(Long taskId, TaskRequest taskStatus);
    public List<TasksResponse> getTasksByTaskListId(Long taskListId);
    public List<TasksResponse> getAllTasks();
}
