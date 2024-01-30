package com.example.task_manager.users.repositories;

import com.example.task_manager.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    // generate SQL problem where example: %l% return first user in table
//    User findUserByEmailContaining(String email);

    User findUserByEmailStartingWith(String email);

    User findUserByEmail(String email);
}
