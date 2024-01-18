package dev.trifanya.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class TaskDTO {
    private int id;
    private String title;
    private String description;
    private String authorEmail;
    private String performerEmail;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDateTime createdAt;
    private LocalDateTime deadline;
}
