package com.SQD20.SQD20.LIVEPROJECT.infrastructure.controller;

import com.SQD20.SQD20.LIVEPROJECT.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/task")
public class TaskController  {

    private final TaskService taskService;

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id){
        taskService.deleteById(id);

        return new ResponseEntity<>("Task Deleted Successfully!", HttpStatus.NO_CONTENT);
    }

}
