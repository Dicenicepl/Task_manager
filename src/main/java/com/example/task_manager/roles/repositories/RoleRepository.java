package com.example.task_manager.roles.repositories;

import com.example.task_manager.roles.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByEmail(String email);
}
