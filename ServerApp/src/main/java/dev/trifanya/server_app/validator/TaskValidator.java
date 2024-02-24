package dev.trifanya.server_app.validator;

import dev.trifanya.server_app.model.Task;
import dev.trifanya.server_app.exception.InvalidDataException;

import java.time.LocalDateTime;

public class TaskValidator {

    public void validateTask(Task task) {
        if (task.getDeadline().isBefore(LocalDateTime.now())) {
            throw new InvalidDataException("Дедлайн должен быть в будущем.");
        } else if (task.getAuthor().equals(task.getPerformer())) {
            System.out.println("Метка");
            throw new InvalidDataException("Исполнителем задачи не может быть ее автор.");
        }
    }
}
