package com.example.task_manager.tokens.repositories;

import com.example.task_manager.tokens.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {


    Token findByToken(String token);

    void deleteByToken(String token);

    Token findByAssignedEmail(String token);
}
