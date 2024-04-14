package com.SQD20.SQD20.LIVEPROJECT.service.impl;

import com.SQD20.SQD20.LIVEPROJECT.infrastructure.exception.TaskNotFoundException;
import com.SQD20.SQD20.LIVEPROJECT.domain.entites.Task;
import com.SQD20.SQD20.LIVEPROJECT.domain.enums.PriorityLevel;
import com.SQD20.SQD20.LIVEPROJECT.domain.enums.Status;
import com.SQD20.SQD20.LIVEPROJECT.repository.TaskRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {


        @Mock
        private TaskRepository taskRepository;

        @InjectMocks
        private TaskServiceImpl taskService;

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

            taskService.deleteById(taskId);

            verify(taskRepository).delete(task);
        }

        @Test
        void deleteById_InvalidId() {

            Long invalidId = 100L;
            when(taskRepository.findById(invalidId)).thenReturn(Optional.empty());

            assertThrows(TaskNotFoundException.class, () -> taskService.deleteById(invalidId));
        }

        @Test
        void deleteById_ExceptionThrown() {

            Long taskId = 1L;
            when(taskRepository.findById(taskId)).thenThrow(new TaskNotFoundException("Testing that the right exception is thrown"));

            assertThrows(TaskNotFoundException.class, () -> taskService.deleteById(taskId));
        }


}