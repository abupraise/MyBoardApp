package com.SQD20.SQD20.LIVEPROJECT.repository;

import com.SQD20.SQD20.LIVEPROJECT.domain.entites.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByTaskListId(Long taskListId);
    List<Task> findByUserId(Long userId);

}
