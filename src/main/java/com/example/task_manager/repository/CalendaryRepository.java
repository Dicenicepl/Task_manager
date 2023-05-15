package com.example.task_manager.repository;

import com.example.task_manager.entity.AppCalendary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

@Repository
public interface CalendaryRepository extends JpaRepository<AppCalendary,Long>{
    AppCalendary[] findAllAppCalendariesByDate(Date date);
    @Modifying
    @Transactional
    @Query("update AppCalendary u set u.isSended = :isSended where u.id = :id")
    void updateIsSended(@Param(value = "id") Long id, @Param(value = "isSended") Boolean isSended);
}
