package com.SQD20.SQD20.LIVEPROJECT.service.impl;

import com.SQD20.SQD20.LIVEPROJECT.domain.entites.AppUser;
import com.SQD20.SQD20.LIVEPROJECT.domain.entites.TaskList;
import com.SQD20.SQD20.LIVEPROJECT.infrastructure.exception.TaskListNotFoundException;
import com.SQD20.SQD20.LIVEPROJECT.infrastructure.exception.UserNotFoundException;
import com.SQD20.SQD20.LIVEPROJECT.payload.request.TaskListRequest;
import com.SQD20.SQD20.LIVEPROJECT.payload.response.TaskResponse;
import com.SQD20.SQD20.LIVEPROJECT.repository.TaskListRepository;
import com.SQD20.SQD20.LIVEPROJECT.repository.TaskRepository;
import com.SQD20.SQD20.LIVEPROJECT.repository.UserRepository;
import com.SQD20.SQD20.LIVEPROJECT.utils.TaskListUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class TaskListServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskListRepository taskListRepository;

    @InjectMocks
    private TaskListServiceImpl taskListService;

    @Mock
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void updateTaskListByUserId_WithValidIdAndRequest_ShouldUpdateTaskList() {
        // Arrange
        Long taskListId = 1L;
        TaskListRequest request = new TaskListRequest();
        request.setTitle("New Title");
        request.setDescription("New Description");

        TaskList existingTaskList = new TaskList();
        existingTaskList.setTitle("Old Title");
        existingTaskList.setDescription("Old Description");

        when(taskListRepository.findById(anyLong())).thenReturn(Optional.of(existingTaskList));
        when(taskListRepository.save(existingTaskList)).thenReturn(existingTaskList);

        // Act
        TaskList updatedTaskList = taskListService.updateTaskListByUserId(taskListId, request);

        // Assert
        assertEquals(request.getTitle(), updatedTaskList.getTitle());
        assertEquals(request.getDescription(), updatedTaskList.getDescription());
        verify(taskListRepository, times(1)).findById(taskListId);
        verify(taskListRepository, times(1)).save(existingTaskList);
    }

    @Test
    void updateTaskListByUserId_WithInvalidId_ShouldThrowException() {
        // Arrange
        Long taskListId = 1L;
        TaskListRequest request = new TaskListRequest();

        when(taskListRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(TaskListNotFoundException.class, () -> taskListService.updateTaskListByUserId(taskListId, request));
        verify(taskListRepository, times(1)).findById(taskListId);
        verify(taskListRepository, never()).save(any());
    }


    @Test
    void testCreateTaskList_Success() {
        // Mocking data
        Long userId = 1L;
        TaskListRequest request = new TaskListRequest("Test Title", "Test Description");
        AppUser user = new AppUser();
        user.setId(userId);

        // Mocking behavior
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));

        // Method call
        TaskResponse response = taskListService.createTaskList(userId, request);

        // Verifications
        verify(userRepository, times(1)).findById(userId);
        verify(taskListRepository, times(1)).save(any(TaskList.class));
        assertNotNull(response);
        assertEquals(TaskListUtils.TASK_LIST_CREATION_SUCCESS_CODE, response.getResponseCode());
        assertEquals(TaskListUtils.TASK_LIST_CREATION_MESSAGE, response.getResponseMessage());
    }

    @Test
    void testCreateTaskList_UserNotFound() {
        // Mocking data
        Long userId = 1L;
        TaskListRequest request = new TaskListRequest("Test Title", "Test Description");

        // Mocking behavior
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.empty());

        // Method call and verification
        assertThrows(UserNotFoundException.class, () -> taskListService.createTaskList(userId, request));
        verify(userRepository, times(1)).findById(userId);
        verify(taskListRepository, never()).save(any(TaskList.class));
    }
    @Test
    void testDeleteTaskListById() {

        AppUser user = new AppUser();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setIsEnabled(true);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setPhoneNumber("6625550144");
        user.setTaskList(new ArrayList<>());

        TaskList taskList = new TaskList();
        taskList.setDescription("The characteristics of someone or something");
        taskList.setTasks(new ArrayList<>());
        taskList.setTitle("Dr");
        taskList.setUser(user);
        Optional<TaskList> ofResult = Optional.of(taskList);
        doNothing().when(taskListRepository).delete(Mockito.any());
        when(taskListRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        doNothing().when(taskListRepository).deleteAll(Mockito.any());

        assertNotNull(taskListService.deleteTask(1L));
    }
}
