package com.example.task_manager.projects.repositories;

import com.example.task_manager.projects.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project findByName(String name);
}
