package com.SQD20.SQD20.LIVEPROJECT.service.impl;

import com.SQD20.SQD20.LIVEPROJECT.domain.entites.Task;
import com.SQD20.SQD20.LIVEPROJECT.domain.entites.TaskList;
import com.SQD20.SQD20.LIVEPROJECT.infrastructure.exception.TaskListNotFoundException;
import com.SQD20.SQD20.LIVEPROJECT.infrastructure.exception.TaskNotFoundException;
import com.SQD20.SQD20.LIVEPROJECT.payload.request.TaskListRequest;
import com.SQD20.SQD20.LIVEPROJECT.payload.request.TaskRequest;
import com.SQD20.SQD20.LIVEPROJECT.repository.TaskListRepository;
import com.SQD20.SQD20.LIVEPROJECT.repository.TaskRepository;
import com.SQD20.SQD20.LIVEPROJECT.service.TaskListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskListServiceImpl implements TaskListService {

    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;

    @Transactional
    @Override
    public TaskList updateTaskListByUserId(Long taskListId, TaskListRequest taskListRequest) {
        TaskList taskList = taskListRepository.findById(taskListId)
                .orElseThrow(() -> new TaskListNotFoundException("Task list not found with id " + taskListId));

        // Update task list fields
        if (taskListRequest.getTitle() != null) {
            taskList.setTitle(taskListRequest.getTitle()); // Update the title field of the fetched TaskList entity
        }
        if (taskListRequest.getDescription() != null){
            taskList.setDescription(taskListRequest.getDescription()); // Update the description field of the fetched TaskList entity
        }

        return taskListRepository.save(taskList); // Save the updated TaskList entity
    }

}
