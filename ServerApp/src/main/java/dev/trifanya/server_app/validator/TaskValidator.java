package dev.trifanya.server_app.validator;

import dev.trifanya.server_app.model.Task;
import dev.trifanya.server_app.exception.InvalidDataException;

import java.time.LocalDateTime;

public class TaskValidator {

    public void validateTask(Task task) {
        if (task.getTitle().isEmpty()) throw new InvalidDataException("Необходимо указать название задачи.");
        if (task.getPriority() == null) throw new InvalidDataException("Необходимо указать приоритет задачи.");
        if (task.getStatus() == null) throw new InvalidDataException("Необходимо указать статус задачи.");
        if (task.getPerformer() == null) throw new InvalidDataException("Необходимо указать исполнителя задачи.");
        if (task.getDeadline() == null) throw new InvalidDataException("Необходимо указать дедлайн задачи.");
        if (task.getDeadline().isBefore(LocalDateTime.now())) throw new InvalidDataException("Дедлайн должен быть в будущем.");
        if (task.getAuthor().equals(task.getPerformer())) throw new InvalidDataException("Исполнителем задачи не может быть ее автор.");
    }
}
