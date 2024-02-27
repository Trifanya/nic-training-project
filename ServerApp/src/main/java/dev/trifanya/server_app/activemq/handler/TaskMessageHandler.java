package dev.trifanya.server_app.activemq.handler;

import dev.trifanya.server_app.model.Task;
import dev.trifanya.server_app.service.TaskService;
import dev.trifanya.server_app.activemq.producer.TaskMessageProducer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.HashMap;
import javax.jms.Destination;
import javax.jms.TextMessage;
import javax.jms.JMSException;

public class TaskMessageHandler {
    private static final Logger logger = LogManager.getLogger(TaskMessageHandler.class);

    private final TaskService taskService;
    private final ObjectMapper objectMapper;
    private final TaskMessageProducer taskMessageProducer;

    public TaskMessageHandler() {
        taskService = new TaskService();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        taskMessageProducer = new TaskMessageProducer();
    }

    public void handleGetSingleTask(Destination responseDestination, TextMessage textMessage) {
        logger.trace("Вызван метод handleGetSingleTask().");
        try {
            int taskId = objectMapper.readValue(textMessage.getText(), Integer.class);
            taskMessageProducer.sendTask(responseDestination, taskService.getTaskById(taskId));
        } catch (JMSException | JsonProcessingException exception) {
            logger.error("Произошла ошибка при обработке или отправке сообщения.");
            taskMessageProducer.sendErrorMessage(responseDestination, "Не удалось получить данные о задаче.");
        }
    }

    public void handleGetTaskList(Destination responseDestination, TextMessage textMessage) {
        logger.trace("Вызван метод handleGetTaskList().");
        try {
            Map<String, String> taskRequestParams = objectMapper.readValue(textMessage.getText(),
                    new TypeReference<HashMap<String, String>>() {});
            taskMessageProducer.sendTaskList(responseDestination, taskService.getTasks(taskRequestParams));
        } catch (JMSException | JsonProcessingException exception) {
            logger.error("Произошла ошибка при обработке или отправке сообщения.");
            taskMessageProducer.sendErrorMessage(responseDestination, "Не удалось получить список задач.");
        }
    }

    public void handleCreateTask(Destination responseDestination, TextMessage textMessage) {
        logger.trace("Вызван метод handleCreateTask().");
        try {
            Task taskToSave = objectMapper.readValue(textMessage.getText(), Task.class);
            taskService.createNewTask(taskToSave);
            taskMessageProducer.sendSuccessMessage(responseDestination, "Задача успешно добавлена.");
        } catch (JMSException | JsonProcessingException exception) {
            logger.error("Произошла ошибка при обработке или отправке сообщения.");
            taskMessageProducer.sendErrorMessage(responseDestination, "Не удалось создать задачу.");
        } catch (Exception exception) {
            logger.warn("Произошла ошибка при попытке создать задачу.");
            taskMessageProducer.sendErrorMessage(responseDestination, exception.getMessage());
        }
    }

    public void handleUpdateTask(Destination responseDestination, TextMessage textMessage) {
        logger.trace("Вызван метод handleUpdateTask().");
        try {
            Task updatedTask = objectMapper.readValue(textMessage.getText(), Task.class);
            taskService.updateTaskInfo(updatedTask);
            taskMessageProducer.sendSuccessMessage(responseDestination, "Задача успешно обновлена.");
        } catch (JMSException | JsonProcessingException exception) {
            logger.error("Произошла ошибка при обработке или отправке сообщения.");
            taskMessageProducer.sendErrorMessage(responseDestination, "Не удалось обновить информация о задаче.");
        } catch (Exception exception) {
            logger.warn("Произошла ошибка при попытке обновить данные о задаче.");
            taskMessageProducer.sendErrorMessage(responseDestination, exception.getMessage());
        }
    }

    public void handleDeleteTask(Destination responseDestination, TextMessage textMessage) {
        logger.trace("Вызван метод handleDeleteTask().");
        try {
            int taskToDeleteId = objectMapper.readValue(textMessage.getText(), Integer.class);
            taskService.deleteTaskById(taskToDeleteId);
            taskMessageProducer.sendSuccessMessage(responseDestination, "Задача успешно удалена.");
        } catch (JMSException | JsonProcessingException exception) {
            logger.error("Произошла ошибка при обработке или отправке сообщения.");
            taskMessageProducer.sendErrorMessage(responseDestination, "Не удалось удалить задачу.");
        } catch (Exception exception) {
            logger.warn("Произошла ошибка при попытке удалить задачу.");
            taskMessageProducer.sendErrorMessage(responseDestination, exception.getMessage());
        }
    }
}
