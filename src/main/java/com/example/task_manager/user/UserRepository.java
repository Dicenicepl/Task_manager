package com.example.task_manager.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(Long id);
    Optional<User> findUserByEmail(String email);

    @Transactional
    void deleteByEmail(String email);

    @Modifying
    @Transactional
    @Query("update User u set u.token = :token where u.id = :id")
    void addToken(Long id, String token);
}
