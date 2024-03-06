package dev.trifanya.server_app.service;

import dev.trifanya.server_app.exception.NotFoundException;
import dev.trifanya.server_app.model.Task;
import dev.trifanya.server_app.mybatis.criteria_builder.TaskFiltersBuilder;

import dev.trifanya.server_app.repository.TaskRepository;
import dev.trifanya.server_app.validator.TaskValidator;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;

import java.util.Map;
import java.util.List;

@RequiredArgsConstructor
public class TaskService {
    private static final Logger logger = LogManager.getLogger(TaskService.class);
    private static final String NOT_FOUND_MSG = "Задача с указанным ID не найдена.";

    private final TaskValidator taskValidator;
    private final TaskRepository taskRepository;
    private final TaskFiltersBuilder taskFiltersBuilder;

    public TaskService() {
        taskValidator = new TaskValidator();
        taskRepository = new TaskRepository();
        taskFiltersBuilder = new TaskFiltersBuilder();
    }

    public Task getTaskById(int taskId) {
        logger.trace("Вызван метод getTaskById().");
        return taskRepository.getTaskById(taskId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_MSG));
    }

    public List<Task> getTasks(Map<String, String> requestParams) {
        logger.trace("Вызван метод getTasks().");
        String sortBy = requestParams.remove("sortBy");
        String sortDir = requestParams.remove("sortDir");
        if (sortBy == null) sortBy = "id";
        if (sortDir == null) sortDir = "ASC";
        SelectStatementProvider selectStatement = taskFiltersBuilder.generateSelectStatement(requestParams, sortBy, sortDir);
        return taskRepository.getTaskList(selectStatement);
    }

    public void createNewTask(Task taskToSave) {
        logger.trace("Вызван метод createNewTask().");
        taskValidator.validateTask(taskToSave);
        taskRepository.createNewTask(taskToSave);
    }

    public void updateTaskInfo(Task updatedTask) {
        logger.trace("Вызван метод updateTaskInfo().");
        taskValidator.validateTask(updatedTask);
        Task taskToUpdate = getTaskById(updatedTask.getId());
        updatedTask.setCreatedAt(taskToUpdate.getCreatedAt());
        taskRepository.updateTaskInfo(updatedTask);
    }

    public void deleteTaskById(int taskToDeleteId) {
        logger.trace("Вызван метод deleteTaskById().");
        taskRepository.deleteTaskById(taskToDeleteId);
    }
}
