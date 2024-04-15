package com.SQD20.SQD20.LIVEPROJECT.service.impl;

import com.SQD20.SQD20.LIVEPROJECT.domain.entites.AppUser;
import com.SQD20.SQD20.LIVEPROJECT.domain.entites.Task;
import com.SQD20.SQD20.LIVEPROJECT.domain.entites.TaskList;
import com.SQD20.SQD20.LIVEPROJECT.domain.enums.PriorityLevel;
import com.SQD20.SQD20.LIVEPROJECT.domain.enums.Status;
import com.SQD20.SQD20.LIVEPROJECT.infrastructure.exception.TaskNotFoundException;
import com.SQD20.SQD20.LIVEPROJECT.payload.request.TaskRequest;
import com.SQD20.SQD20.LIVEPROJECT.repository.TaskListRepository;
import com.SQD20.SQD20.LIVEPROJECT.repository.TaskRepository;

import com.SQD20.SQD20.LIVEPROJECT.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskListRepository taskListRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void updateTask_WhenTaskExists_UpdatesSuccessfully() {
        // Arrange
        Long taskId = 1L;
        Task existingTask = new Task();
        existingTask.setTitle("Old Title");

        TaskRequest request = new TaskRequest();
        request.setTitle("New Title");
        request.setDescription("New Description");
        request.setDeadline(LocalDateTime.now());
        request.setPriorityLevel(PriorityLevel.HIGH);
        request.setStatus(Status.IN_PROGRESS);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));

        // Act
        taskService.updateTask(taskId, request);

        // Assert
        verify(taskRepository).save(existingTask);
        assertEquals("New Title", existingTask.getTitle());
        assertEquals("New Description", existingTask.getDescription());
        assertNotNull(existingTask.getDeadline());
        assertEquals(PriorityLevel.HIGH, existingTask.getPriorityLevel());
        assertEquals(Status.IN_PROGRESS, existingTask.getStatus());
    }

    @Test
    void updateTask_WhenTaskDoesNotExist_ThrowsException() {
        // Arrange
        Long taskId = 1L;
        TaskRequest request = new TaskRequest();

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(taskId, request));
    }

    @Test
    void deleteById_ValidId() {
        // Arrange
        Long taskId = 1L;
        Task task = new Task();
        task.setTitle("Test");
        task.setDescription("Write test");
        task.setStatus(Status.PENDING);
        task.setPriorityLevel(PriorityLevel.HIGH);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        taskService.deleteTask(taskId);

        verify(taskRepository).delete(task);
    }

    @Test
    void deleteById_InvalidId() {

        Long invalidId = 100L;
        when(taskRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(invalidId));
    }

    @Test
    void deleteById_ExceptionThrown() {

        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenThrow(new TaskNotFoundException("Testing that the right exception is thrown"));

        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(taskId));
    }

    @Test
    public void testCreateTask() {

        Long userId = 1L;
        Long taskListId = 1L;
        AppUser user = new AppUser();
        TaskList taskList = new TaskList();

        TaskRequest createRequest = new TaskRequest();
        createRequest.setTitle("Task Title");
        createRequest.setDescription("Task Description");
        createRequest.setDeadline(LocalDateTime.now());
        createRequest.setPriorityLevel(PriorityLevel.HIGH);
        createRequest.setStatus(Status.IN_PROGRESS);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(taskListRepository.findById(taskListId)).thenReturn(Optional.of(taskList));

        taskService.createTask(userId, taskListId, createRequest);

        verify(taskRepository, times(1)).save(any(Task.class));
    }
}