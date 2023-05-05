package com.example.task_manager.repository;

import com.example.task_manager.entity.AppCalendary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface CalendaryRepository extends JpaRepository<AppCalendary,Long>{
    AppCalendary[] findAllAppCalendariesByDate(Date date);
}
