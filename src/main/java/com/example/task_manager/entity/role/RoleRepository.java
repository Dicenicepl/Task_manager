package com.example.task_manager.entity.role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<ERole, Integer> {
    ERole findERoleByEmail(String email);
}
