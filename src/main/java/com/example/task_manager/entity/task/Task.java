package com.example.task_manager.entity.task;

import java.sql.Date;
import java.sql.Time;

import com.example.task_manager.entity.project.Project;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @SequenceGenerator(
            name = "sequence",
            sequenceName = "sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    private Long task_id;
    private String owner_email;
    @Column(unique = true)
    private String name;
    private String description;
    private Date createdDate = new Date(System.currentTimeMillis());
    private Time createdTime = new Time(System.currentTimeMillis());
    private Date deadLineDate;
    private Time deadLineTime;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;


}
