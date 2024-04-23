package com.SQD20.SQD20.LIVEPROJECT.infrastructure.controller;

import com.SQD20.SQD20.LIVEPROJECT.domain.entites.TaskList;
import com.SQD20.SQD20.LIVEPROJECT.payload.request.TaskListRequest;
import com.SQD20.SQD20.LIVEPROJECT.payload.response.TaskListResponse;
import com.SQD20.SQD20.LIVEPROJECT.service.TaskListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/task-list")
public class TaskListController {

    private final TaskListService taskListService;

    @PatchMapping("/update-tasklist/{id}")
    public ResponseEntity<TaskList> updateTaskList(@PathVariable Long id, @RequestBody TaskListRequest taskListRequest) {
        TaskList taskList = taskListService.updateTaskListByUserId(id, taskListRequest);
        if(taskList != null){
            return new ResponseEntity<>(taskList, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


    @PostMapping("/create-task-list/{id}")
    public ResponseEntity<TaskListResponse> createTaskList(@PathVariable Long id, @RequestBody TaskListRequest request){
        TaskListResponse createdList = taskListService.createTaskList(id, request);
        return  new ResponseEntity<>(createdList,HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-task-list/{id}")
    public ResponseEntity<TaskList> deleteTask(@PathVariable long id){
       TaskList deletedTaskList = taskListService.deleteTask(id);
        if(deletedTaskList != null){
            return new ResponseEntity<>(deletedTaskList, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/get-task-list/{id}")
    public ResponseEntity<List<TaskListResponse>> getAllTaskList(@PathVariable(value = "id") long userId){
        return new ResponseEntity<>(taskListService.getAllTaskList(userId),HttpStatus.OK);
    }

}
