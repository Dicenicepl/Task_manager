package com.example.task_manager.roles.repositories;

import com.example.task_manager.roles.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Collection<Role> findRolesByEmail(String email);

    Role findRoleByEmail(String email);
}
