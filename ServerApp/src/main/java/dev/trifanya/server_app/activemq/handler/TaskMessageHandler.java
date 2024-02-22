package dev.trifanya.server_app.activemq.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.trifanya.server_app.activemq.producer.TaskMessageProducer;
import dev.trifanya.server_app.model.Task;
import dev.trifanya.server_app.service.TaskService;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.HashMap;
import java.util.Map;

public class TaskMessageHandler {
    private final ObjectMapper objectMapper;
    private final TaskService taskService;
    private final TaskMessageProducer taskMessageProducer;

    public TaskMessageHandler() throws JMSException {
        this.objectMapper = new ObjectMapper();
        this.taskService = new TaskService();
        this.taskMessageProducer = new TaskMessageProducer();
    }

    public void handleGetSingleTask(Destination responseDestination, TextMessage textMessage) throws JMSException, JsonProcessingException {
        int taskId = objectMapper.readValue(textMessage.getText(), Integer.class);
        taskMessageProducer.sendTask(responseDestination, taskService.getTaskById(taskId));
    }

    public void handleGetTaskList(Destination responseDestination, TextMessage textMessage) throws JMSException, JsonProcessingException {
        Map<String, String> taskRequestParams = objectMapper.readValue(textMessage.getText(), new TypeReference<HashMap<String, String>>() {});
        String taskSortBy = taskRequestParams.remove("sortBy");
        String taskSortDir = taskRequestParams.remove("sortDir");
        taskMessageProducer.sendTaskList(
                responseDestination,
                taskService.getTasks(taskRequestParams, taskSortBy, taskSortDir));
    }

    public void handlePostTask(Destination responseDestination, TextMessage textMessage) throws JMSException, JsonProcessingException {
        Task taskToSave = objectMapper.readValue(textMessage.getText(), Task.class);
        try {
            taskService.createNewTask(taskToSave);
            taskMessageProducer.sendSuccessMessage(responseDestination, "Задача успешно добавлена.");
        } catch (Exception exception) {
            taskMessageProducer.sendErrorMessage(responseDestination, exception.getMessage());
        }
    }

    public void handleUpdateTask(Destination responseDestination, TextMessage textMessage) throws JMSException, JsonProcessingException {
        Task updatedTask = objectMapper.readValue(textMessage.getText(), Task.class);
        try {
            taskService.updateTaskInfo(updatedTask);
            taskMessageProducer.sendSuccessMessage(responseDestination, "Задача успешно обновлена.");
        } catch (Exception exception) {
            taskMessageProducer.sendErrorMessage(responseDestination, exception.getMessage());
        }
    }

    public void handleDeleteTask(Destination responseDestination, TextMessage textMessage) throws JMSException, JsonProcessingException {
        int taskToDeleteId = objectMapper.readValue(textMessage.getText(), Integer.class);
        try {
            taskService.deleteTaskById(taskToDeleteId);
            taskMessageProducer.sendSuccessMessage(responseDestination, "Задача успешно удалена.");
        } catch (Exception exception) {
            taskMessageProducer.sendErrorMessage(responseDestination, exception.getMessage());
        }
    }
}
