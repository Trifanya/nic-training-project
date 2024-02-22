package dev.trifanya.swing_app.activemq.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.trifanya.server_app.model.Task;
import dev.trifanya.swing_app.SwingApp;
import dev.trifanya.swing_app.swing.MainFrame;

import javax.jms.*;

import java.util.HashMap;
import java.util.Map;

import static javax.jms.Session.AUTO_ACKNOWLEDGE;

public class TaskMessageProducer {
    private final Session session;
    private final Destination destination;
    private final MessageProducer producer;
    private final ObjectMapper objectMapper;

    private final MainFrame mainFrame;

    public TaskMessageProducer(MainFrame mainFrame) throws JMSException {
        session = SwingApp.connection.createSession(false, AUTO_ACKNOWLEDGE);
        destination = session.createQueue(SwingApp.REQUEST_FROM_SWING_CLIENT_QUEUE);
        producer = session.createProducer(destination);
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        this.mainFrame = mainFrame;
    }

    public void sendGetTaskMessage(int taskId) throws JMSException, JsonProcessingException {
        System.out.println("Вызван метод sendGetTaskMessage().");
        String messageToSend = objectMapper.writeValueAsString(taskId);
        TextMessage message = session.createTextMessage(messageToSend);
        message.setStringProperty("Request name", "Get single task");
        producer.send(destination, message);
    }

    public void sendGetTaskListMessage(Map<String, String> requestParams) throws JsonProcessingException, JMSException {
        System.out.println("Вызван метод sendGetTaskListMessage().");
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
    }

    public void sendCreateTaskMessage(Task taskToSave) throws JMSException, JsonProcessingException {
        System.out.println("Вызван метод sendCreateTaskMessage().");
        String messageToSend = objectMapper.writeValueAsString(taskToSave);
        TextMessage message = session.createTextMessage(messageToSend);
        message.setStringProperty("Request name", "Post task");
        producer.send(destination, message);
    }

    public void sendUpdateTaskMessage(Task updatedTask) throws JMSException, JsonProcessingException {
        System.out.println("Вызван метод sendUpdateTaskMessage().");
        String messageToSend = objectMapper.writeValueAsString(updatedTask);
        TextMessage message = session.createTextMessage(messageToSend);
        message.setStringProperty("Request name", "Update task");
        producer.send(destination, message);
    }

    public void sendDeleteTaskMessage(int taskToDeleteId) throws JsonProcessingException, JMSException {
        System.out.println("Вызван метод sendDeleteTaskMessage().");
        String messageToSend = objectMapper.writeValueAsString(taskToDeleteId);
        TextMessage message = session.createTextMessage(messageToSend);
        message.setStringProperty("Request name", "Delete task");
        producer.send(destination, message);
    }
}
