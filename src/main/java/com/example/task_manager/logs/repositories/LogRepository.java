package com.example.task_manager.logs.repositories;

import com.example.task_manager.logs.entities.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Long> {
}
