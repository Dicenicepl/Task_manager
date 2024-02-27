package com.example.task_manager.users.repositories;

import com.example.task_manager.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {


    List<User> findUserByEmailStartingWith(String email);

    User findUserByEmail(String email);
}
