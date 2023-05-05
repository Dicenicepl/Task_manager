package com.example.task_manager.repository;

import com.example.task_manager.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    AppUser findAppUserByEmail(String email);
    void deleteAppUserByEmail(String email);
}
