package com.example.task_manager.entity.log;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Table(name = "Logs")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class LogClass {
    @Id
    @GeneratedValue
    Long id;
    Date changeDataLog = new Date(System.currentTimeMillis());
    String changes;
    String byWho;

    public LogClass(String changes, String byWho) {
        this.changes = changes;
        this.byWho = byWho;
    }
}
