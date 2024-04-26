package com.SQD20.SQD20.LIVEPROJECT.service.impl;

import com.SQD20.SQD20.LIVEPROJECT.domain.entites.AppUser;
import com.SQD20.SQD20.LIVEPROJECT.domain.entites.Task;
import com.SQD20.SQD20.LIVEPROJECT.domain.entites.TaskList;
import com.SQD20.SQD20.LIVEPROJECT.domain.enums.PriorityLevel;
import com.SQD20.SQD20.LIVEPROJECT.domain.enums.Status;
import com.SQD20.SQD20.LIVEPROJECT.infrastructure.exception.TaskListNotFoundException;
import com.SQD20.SQD20.LIVEPROJECT.infrastructure.exception.TaskNotFoundException;
import com.SQD20.SQD20.LIVEPROJECT.infrastructure.exception.UsernameNotFoundException;
import com.SQD20.SQD20.LIVEPROJECT.payload.request.TaskRequest;
import com.SQD20.SQD20.LIVEPROJECT.payload.response.TasksResponse;
import com.SQD20.SQD20.LIVEPROJECT.repository.TaskListRepository;
import com.SQD20.SQD20.LIVEPROJECT.repository.TaskRepository;

import com.SQD20.SQD20.LIVEPROJECT.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
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
    void testCreateTask_SuccessfullyCreatesTask() {
        // Arrange
        Long userId = 1L;
        Long taskListId = 1L;
        TaskRequest createRequest = new TaskRequest();
        createRequest.setTitle("New Task");
        createRequest.setDescription("This is a test task");
        createRequest.setDeadline(LocalDateTime.now().plusDays(1));
        createRequest.setPriorityLevel(PriorityLevel.HIGH);
        createRequest.setStatus(Status.PENDING);

        AppUser user = new AppUser();
        user.setId(userId);
        TaskList taskList = new TaskList();
        taskList.setId(taskListId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(taskListRepository.findById(taskListId)).thenReturn(Optional.of(taskList));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        TaskRequest result = taskService.createTask(userId, taskListId, createRequest);

        // Assert
        assertNotNull(result);
        assertEquals("New Task", result.getTitle());
        assertEquals("This is a test task", result.getDescription());
        assertNotNull(result.getDeadline());
        assertEquals(PriorityLevel.HIGH, result.getPriorityLevel());
        assertEquals(Status.PENDING, result.getStatus());

        verify(userRepository).findById(userId);
        verify(taskListRepository).findById(taskListId);
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void testCreateTaskWithNonexistentUser() {
        // Arrange
        Long userId = 99L; // Assuming this user ID does not exist
        Long taskListId = 1L;
        TaskRequest createRequest = new TaskRequest();
        createRequest.setTitle("Test Task");

        when(userRepository.findById(userId)).thenThrow(new UsernameNotFoundException("User with ID " + userId + " not found"));

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> taskService.createTask(userId, taskListId, createRequest));
    }

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
    void updateTaskStatus_taskExists_statusUpdated() {
        // Arrange
        Long taskId = 1L;
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setStatus(Status.COMPLETED);

        Task existingTask = new Task();
        existingTask.setId(taskId);
        existingTask.setStatus(Status.PENDING);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));

        // Act
        taskService.updateTaskStatus(taskId, taskRequest);

        // Assert
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).save(existingTask);
        assert(existingTask.getStatus()).equals(Status.COMPLETED);
    }

    @Test
    void updateTaskStatus_taskNotFound_throwException() {
        // Arrange
        Long taskId = 1L;
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setStatus(Status.COMPLETED);

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(TaskNotFoundException.class, () -> {
            taskService.updateTaskStatus(taskId, taskRequest);
        });
    }

    @Test
    void getTasksByTaskListId_ShouldReturnListOfTasksResponse() {
        // Arrange
        Long taskListId = 1L;
        Task mockTask1 = new Task();
        Task mockTask2 = new Task();
        List<Task> mockTasks = Arrays.asList(mockTask1, mockTask2);

        when(taskRepository.findByTaskListId(taskListId)).thenReturn(mockTasks);

        // Act
        List<TasksResponse> results = taskService.getTasksByTaskListId(taskListId);

        // Assert
        assertEquals(2, results.size());
        verify(taskRepository).findByTaskListId(taskListId);
        assertEquals(results.get(0).getTitle(), null);
        assertEquals(results.get(1).getTitle(), null);
    }

}