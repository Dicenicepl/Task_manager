package com.example.task_manager.entity.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserById(Long id);
    Optional<User> findUserByEmail(String email);

    @Transactional
    void deleteByEmail(String email);

    @Modifying
    @Transactional
    @Query("update User u set u.token = :token where u.id = :id")
    void addToken(Long id, String token);

    Optional<User> findUserByToken(String token);


    @Query("update User u set u.token = null where u.token = :token")
    void logout(String token);

    User findUserByExpireToken(Time expireToken);
}
