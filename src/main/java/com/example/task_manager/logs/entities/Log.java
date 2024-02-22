package com.example.task_manager.logs.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Logs")
public class Log {
    @Id
    @SequenceGenerator(
            name = "sequence",
            sequenceName = "sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    private Long log_id;
    private Date dataOfUpdate = new Date(System.currentTimeMillis());
    private Time timeOfUpdate = new Time(System.currentTimeMillis());
    private String changes;
    private String typeOfRequest;
    private String whoUpdated;
    private String urlRequest;
    private boolean isSucceed;


    public Log(String changes, String typeOfRequest, String whoUpdated, String urlRequest,boolean isSucceed) {
        this.changes = changes;
        this.typeOfRequest = typeOfRequest;
        this.whoUpdated = whoUpdated;
        this.urlRequest = urlRequest;
        this.isSucceed = isSucceed;
    }

    public Log(String changes, String typeOfRequest, String whoUpdated, String urlRequest) {
        this.changes = changes;
        this.typeOfRequest = typeOfRequest;
        this.whoUpdated = whoUpdated;
        this.urlRequest = urlRequest;
    }

    @Override
    public String toString() {
        return "Log{" +
                "log_id=" + log_id +
                ", dataOfUpdate=" + dataOfUpdate +
                ", timeOfUpdate=" + timeOfUpdate +
                ", changes='" + changes + '\'' +
                ", whoUpdated='" + whoUpdated + '\'' +
                '}';
    }
}
