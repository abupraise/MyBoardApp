package com.SQD20.SQD20.LIVEPROJECT.service.impl;

import com.SQD20.SQD20.LIVEPROJECT.domain.entites.TaskList;
import com.SQD20.SQD20.LIVEPROJECT.infrastructure.exception.TaskListNotFoundException;
import com.SQD20.SQD20.LIVEPROJECT.payload.request.TaskListRequest;
import com.SQD20.SQD20.LIVEPROJECT.repository.TaskListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class TaskListServiceImplTest {

    @Mock
    private TaskListRepository taskListRepository;

    @InjectMocks
    private TaskListServiceImpl taskListService;

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
}
