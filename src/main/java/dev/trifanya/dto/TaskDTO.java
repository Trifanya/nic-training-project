package dev.trifanya.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class TaskDTO {
    private int id;
    private String title;
    private String description;
    private String authorEmail;
    private String performerEmail;
    private LocalDateTime deadline;
    private TaskStatus status;
    private TaskPriority priority;
}
