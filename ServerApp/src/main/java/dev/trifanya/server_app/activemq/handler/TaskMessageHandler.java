package dev.trifanya.server_app.activemq.handler;

import dev.trifanya.server_app.ServerApp;
import dev.trifanya.server_app.model.Task;
import dev.trifanya.server_app.service.TaskService;
import dev.trifanya.server_app.validator.TaskValidator;
import dev.trifanya.server_app.activemq.producer.TaskMessageProducer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.HashMap;
import javax.jms.Destination;
import javax.jms.TextMessage;
import javax.jms.JMSException;

public class TaskMessageHandler {
    private static final Logger logger = ServerApp.logger;
    private final TaskService taskService;
    private final ObjectMapper objectMapper;
    private final TaskValidator taskValidator;
    private final TaskMessageProducer taskMessageProducer;

    public TaskMessageHandler() throws JMSException {
        taskService = new TaskService();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        taskValidator = new TaskValidator();
        taskMessageProducer = new TaskMessageProducer();
    }

    public void handleGetSingleTask(Destination responseDestination, TextMessage textMessage) {
        logger.trace("TaskMessageHandler: Вызван метод handleGetSingleTask().");
        try {
            int taskId = objectMapper.readValue(textMessage.getText(), Integer.class);
            taskMessageProducer.sendTask(responseDestination, taskService.getTaskById(taskId));
        } catch (JMSException | JsonProcessingException exception) {
            logger.error("TaskMessageHandler: Произошла ошибка при обработке сообщения.");
        }
    }

    public void handleGetTaskList(Destination responseDestination, TextMessage textMessage) {
        logger.trace("TaskMessageHandler: Вызван метод handleGetTaskList().");
        try {
            Map<String, String> taskRequestParams = objectMapper.readValue(textMessage.getText(), new TypeReference<HashMap<String, String>>() {
            });
            String taskSortBy = taskRequestParams.remove("sortBy");
            String taskSortDir = taskRequestParams.remove("sortDir");
            taskMessageProducer.sendTaskList(
                    responseDestination,
                    taskService.getTasks(taskRequestParams, taskSortBy, taskSortDir));
        } catch (JMSException | JsonProcessingException exception) {
            logger.error("TaskMessageHandler: Произошла ошибка при обработке сообщения.");
        }
    }

    public void handleCreateTask(Destination responseDestination, TextMessage textMessage) {
        logger.trace("TaskMessageHandler: Вызван метод handleCreateTask().");
        try {
            Task taskToSave = objectMapper.readValue(textMessage.getText(), Task.class);
            taskValidator.validateTask(taskToSave);
            taskService.createNewTask(taskToSave);
            taskMessageProducer.sendSuccessMessage(responseDestination, "Задача успешно добавлена.");
        } catch (JMSException | JsonProcessingException exception) {
            logger.error("TaskMessageHandler: Произошла ошибка при обработке.");
        } catch (Exception exception) {
            logger.warn("TaskMessageHandler: Произошла ошибка при попытке создать задачу.");
            taskMessageProducer.sendErrorMessage(responseDestination, exception.getMessage());
        }
    }

    public void handleUpdateTask(Destination responseDestination, TextMessage textMessage) {
        logger.trace("TaskMessageHandler: Вызван метод handleUpdateTask().");
        try {
            Task updatedTask = objectMapper.readValue(textMessage.getText(), Task.class);
            taskService.updateTaskInfo(updatedTask);
            taskMessageProducer.sendSuccessMessage(responseDestination, "Задача успешно обновлена.");
        } catch (JMSException | JsonProcessingException exception) {
            logger.error("TaskMessageHandler: Произошла ошибка при обработке сообщения.");
        } catch (Exception exception) {
            logger.warn("TaskMessageHandler: Произошла ошибка при попытке обновить данные о задаче.");
            taskMessageProducer.sendErrorMessage(responseDestination, exception.getMessage());
        }
    }

    public void handleDeleteTask(Destination responseDestination, TextMessage textMessage) {
        logger.trace("TaskMessageHandler: Вызван метод handleDeleteTask().");
        try {
            int taskToDeleteId = objectMapper.readValue(textMessage.getText(), Integer.class);
            taskService.deleteTaskById(taskToDeleteId);
            taskMessageProducer.sendSuccessMessage(responseDestination, "Задача успешно удалена.");
        } catch (JMSException | JsonProcessingException exception) {
            logger.error("TaskMessageHandler: Произошла ошибка при обработке сообщения.");
        } catch (Exception exception) {
            logger.warn("TaskMessageHandler: Произошла ошибка при попытке удалить задачу.");
            taskMessageProducer.sendErrorMessage(responseDestination, exception.getMessage());
        }
    }
}
