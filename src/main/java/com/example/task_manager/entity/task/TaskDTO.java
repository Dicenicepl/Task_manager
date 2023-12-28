package com.example.task_manager.entity.task;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TaskDTO {
    private String email;
    private String name;
    private String description;
}
