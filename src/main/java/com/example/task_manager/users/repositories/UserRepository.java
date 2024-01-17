package com.example.task_manager.users.repositories;

import com.example.task_manager.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByEmailContaining(String email);

    User findUserByEmail(String email);
}
