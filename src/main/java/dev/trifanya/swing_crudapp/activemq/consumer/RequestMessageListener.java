package dev.trifanya.swing_crudapp.activemq.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.trifanya.swing_crudapp.activemq.producer.TaskMessageProducer;
import dev.trifanya.swing_crudapp.activemq.producer.UserMessageProducer;
import dev.trifanya.swing_crudapp.service.TaskService;
import dev.trifanya.swing_crudapp.service.UserService;

import javax.jms.*;
import java.util.HashMap;
import java.util.Map;


public class RequestMessageListener implements MessageListener {
    private TaskMessageProducer taskMessageProducer;
    private UserMessageProducer userMessageProducer;
    private TaskService taskService;
    private UserService userService;
    private ObjectMapper objectMapper;

    public RequestMessageListener() throws JMSException {
        taskMessageProducer = new TaskMessageProducer();
        userMessageProducer = new UserMessageProducer();
        taskService = new TaskService();
        userService = new UserService();
        objectMapper = new ObjectMapper();
    }

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            Destination responseDestination = message.getJMSReplyTo();
            String requestName = textMessage.getStringProperty("Request name");
            switch (requestName) {
                case "Get single task":
                    int taskId = objectMapper.readValue(textMessage.getText(), Integer.class);
                    taskMessageProducer.sendTask(responseDestination, taskService.getTaskById(taskId));
                    break;
                case "Get task list":
                    Map<String, String> taskRequestParams = objectMapper.readValue(textMessage.getText(), new TypeReference<HashMap<String, String>>() {});
                    String taskSortBy = taskRequestParams.remove("sortBy");
                    String taskSortDir = taskRequestParams.remove("sortDir");
                    taskMessageProducer.sendTaskList(
                            responseDestination,
                            taskService.getTasks(taskRequestParams, taskSortBy, taskSortDir));
                    break;
                case "Get single user":
                    int userId = objectMapper.readValue(textMessage.getText(), Integer.class);
                    userMessageProducer.sendUser(responseDestination, userService.getUserById(userId));
                    break;
                case "Get user list":
                    Map<String, String> userRequestParams = objectMapper.readValue(textMessage.getText(), new TypeReference<HashMap<String, String>>() {});
                    String userSortBy = userRequestParams.remove("sortBy");
                    String userSortDir = userRequestParams.remove("sortDir");
                    userMessageProducer.sendUserList(
                            responseDestination,
                            userService.getUsers(userRequestParams, userSortBy, userSortDir));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
