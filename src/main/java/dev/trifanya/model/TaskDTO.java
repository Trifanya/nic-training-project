package dev.trifanya.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class TaskDTO {
    private int id;
    private String title;
    private String description;
    private int authorId;
    private int performerId;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDateTime createdAt;
    private LocalDateTime deadline;
}
