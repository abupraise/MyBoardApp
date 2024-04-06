package com.SQD20.SQD20.LIVEPROJECT.repository;

import com.SQD20.SQD20.LIVEPROJECT.domain.entites.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
