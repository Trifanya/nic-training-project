package dev.trifanya.swing_app.activemq.producer;

import dev.trifanya.server_app.model.Task;
import dev.trifanya.swing_app.SwingClientApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.jms.*;
import java.util.Map;
import java.util.HashMap;

import static javax.jms.Session.AUTO_ACKNOWLEDGE;

public class TaskMessageProducer {
    private final Session session;
    private final Destination destination;
    private final MessageProducer producer;
    private final ObjectMapper objectMapper;

    public TaskMessageProducer() throws JMSException {
        session = SwingClientApp.connection.createSession(false, AUTO_ACKNOWLEDGE);
        destination = session.createQueue(SwingClientApp.REQUEST_FROM_SWING_CLIENT_QUEUE);
        producer = session.createProducer(destination);
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    public void sendGetTaskListMessage(Map<String, String> requestParams) {
        System.out.println("Вызван метод sendGetTaskListMessage().");
        try {
            Map<String, String> notNullParams = new HashMap<>();
            for (Map.Entry<String, String> param : requestParams.entrySet()) {
                if (!param.getValue().equals("")) {
                    notNullParams.put(param.getKey(), param.getValue());
                }
            }
            String messageToSend = objectMapper.writeValueAsString(notNullParams);
            TextMessage message = session.createTextMessage(messageToSend);
            message.setStringProperty("Request name", "Get task list");
            producer.send(destination, message);
        } catch (JMSException | JsonProcessingException exception) {
            System.out.println("TaskMessageProducer: Произошла ошибка при отправке сообщения в методе sendGetTaskListMessage().");
        }
    }

    public void sendCreateTaskMessage(Task taskToSave) {
        System.out.println("Вызван метод sendCreateTaskMessage().");
        try {
            String messageToSend = objectMapper.writeValueAsString(taskToSave);
            TextMessage message = session.createTextMessage(messageToSend);
            message.setStringProperty("Request name", "Create task");
            producer.send(destination, message);
        } catch (JMSException | JsonProcessingException exception) {
            System.out.println("TaskMessageProducer: Произошла ошибка при отправке сообщения в методе sendCreateTaskMessage().");
        }
    }

    public void sendUpdateTaskMessage(Task updatedTask) {
        System.out.println("Вызван метод sendUpdateTaskMessage().");
        try {
            String messageToSend = objectMapper.writeValueAsString(updatedTask);
            TextMessage message = session.createTextMessage(messageToSend);
            message.setStringProperty("Request name", "Update task");
            producer.send(destination, message);
        } catch (JMSException | JsonProcessingException exception) {
            System.out.println("TaskMessageProducer: Произошла ошибка при отправке сообщения в методе sendUpdateTaskMessage().");
        }
    }

    public void sendDeleteTaskMessage(int taskToDeleteId) {
        System.out.println("Вызван метод sendDeleteTaskMessage().");
        try {
            String messageToSend = objectMapper.writeValueAsString(taskToDeleteId);
            TextMessage message = session.createTextMessage(messageToSend);
            message.setStringProperty("Request name", "Delete task");
            producer.send(destination, message);
        } catch (JMSException | JsonProcessingException exception) {
            System.out.println("TaskMessageProducer: Произошла ошибка при отправке сообщения в методе sendDeleteTaskMessage().");
        }
    }
}
