package com.SQD20.SQD20.LIVEPROJECT.infrastructure.controller;

import com.SQD20.SQD20.LIVEPROJECT.payload.request.TaskRequest;
import com.SQD20.SQD20.LIVEPROJECT.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/task")
public class TaskController  {

    private final TaskService taskService;

    @PatchMapping("update/{taskId}")
    public ResponseEntity<String> updateTask(@PathVariable Long taskId, @RequestBody TaskRequest updateRequest) {
        taskService.updateTask(taskId, updateRequest);
        return ResponseEntity.ok("Task updated successfully");
    }
}
