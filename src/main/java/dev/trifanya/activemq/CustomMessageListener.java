package dev.trifanya.activemq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.trifanya.activemq.request.GetTaskListRequest;
import dev.trifanya.activemq.request.GetTaskRequest;
import dev.trifanya.model.Task;
import dev.trifanya.service.TaskService;

import javax.jms.*;
import java.util.HashMap;
import java.util.Map;


public class CustomMessageListener implements MessageListener {
    private TaskMessageProducer taskMessageProducer;
    private TaskService taskService;
    private ObjectMapper objectMapper;

    public CustomMessageListener() throws JMSException {
        taskMessageProducer = new TaskMessageProducer();
        taskService = new TaskService();
        objectMapper = new ObjectMapper();
    }

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        String queryName = null;
        try {
            System.out.println("Request name: " + textMessage.getStringProperty("Request name"));
            queryName = textMessage.getStringProperty("Request name");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            switch (queryName) {
                case "Get single task":
                    getTaskIdAndSendTask(textMessage);
                    break;
                case "Get task list":
                    getTaskFiltersAndSendTaskList(textMessage);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getTaskIdAndSendTask(TextMessage textMessage) throws JMSException {
        GetTaskRequest request = null;
        try {
            request = objectMapper.readValue(textMessage.getText(), GetTaskRequest.class);
            System.out.println("taskId: " + request.getTaskId());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        taskMessageProducer.sendTask(taskService.getTaskById(request.getTaskId()));
    }

    public void getTaskFiltersAndSendTaskList(TextMessage textMessage) throws JMSException {
        GetTaskListRequest request = null;
        try {
            request = objectMapper.readValue(textMessage.getText(), GetTaskListRequest.class);
            System.out.println(request.getRequestTitle() + " " + request.getSortBy());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        taskMessageProducer.sendTaskList(
                taskService.getFilteredTasks(
                        request.getFilters(),
                        request.getSortBy(),
                        request.getSortDir()
                ));
    }
}
