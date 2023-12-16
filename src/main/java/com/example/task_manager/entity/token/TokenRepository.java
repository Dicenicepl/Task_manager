package com.example.task_manager.entity.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    @Modifying
    @Transactional
    @Query("update Token u set u.generatedToken = :generatedToken where u.email = :email")
    void addToken(String email, String generatedToken);

    Token findTokenByGeneratedToken(String generatedToken);

    @Modifying
    @Transactional
    @Query("delete from Token u where u.generatedToken = :generatedToken or u.generatedToken is null")
    void deleteExpiredToken(@Param("generatedToken") String generatedToken);

    @Modifying
    @Transactional
    @Query("update Token u set u.expireTime = :newExpireTime where u.generatedToken = :generatedToken")
    void updateExpireTokenToActive(String generatedToken, Time newExpireTime);

    @Query("SELECT u FROM Token u WHERE u.expireTime = :expireTime OR u.expireTime IS NULL")
    List<Token> findAllExpireTime(@Param("expireTime") Time expireTime);
    
}
