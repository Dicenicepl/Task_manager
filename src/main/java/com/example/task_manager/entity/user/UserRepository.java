package com.example.task_manager.entity.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(Long id);
    User findUserByEmail(String email);

    @Transactional
    void deleteByEmail(String email);

    @Modifying
    @Transactional
    @Query("update User u set u.token = :token where u.id = :id")
    void addToken(Long id, String token);

    Optional<User> findUserByToken(String token);

    List<User> findAllByExpireTimeBefore(Time expireTime);

    @Modifying
    @Transactional
    @Query("update User u set u.token = null where u.token = :token")
    void updateTokenToNull(String token);

    @Modifying
    @Transactional
    @Query("update User u set u.expireTime = :newExpireTime where u.token = :token")
    void updateExpireTokenToActive(String token, Time newExpireTime);
}
