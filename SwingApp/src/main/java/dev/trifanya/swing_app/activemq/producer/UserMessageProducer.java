package dev.trifanya.swing_app.activemq.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.trifanya.server_app.model.User;
import dev.trifanya.swing_app.SwingApp;
import dev.trifanya.swing_app.swing.MainFrame;

import javax.jms.*;

import java.util.HashMap;
import java.util.Map;

import static javax.jms.Session.AUTO_ACKNOWLEDGE;

public class UserMessageProducer {
    private final Session session;
    private final Destination destination;
    private final MessageProducer producer;
    private final ObjectMapper objectMapper;

    private final MainFrame mainFrame;

    public UserMessageProducer(MainFrame mainFrame) throws JMSException {
        session = SwingApp.connection.createSession(false, AUTO_ACKNOWLEDGE);
        destination = session.createQueue(SwingApp.REQUEST_FROM_SWING_CLIENT_QUEUE);
        producer = session.createProducer(destination);
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        this.mainFrame = mainFrame;
    }

    public void sendSignInMessage(String email, String password) throws JMSException {
        System.out.println("Вызван метод sendSignInMessage().");
        TextMessage message = session.createTextMessage(email + " " + password);
        message.setStringProperty("Request name", "Sign in");

        producer.send(destination, message);
    }

    public void sendGetUserMessage(int taskId) throws JMSException, JsonProcessingException {
        System.out.println("Вызван метод sendGetUserMessage().");
        String messageToSend = objectMapper.writeValueAsString(taskId);
        TextMessage message = session.createTextMessage(messageToSend);
        message.setStringProperty("Request name", "Get single user");

        producer.send(destination, message);
    }

    public void sendGetUserListMessage(Map<String, String> requestParams) throws JsonProcessingException, JMSException {
        System.out.println("Вызван метод sendGetUserListMessage().");
        Map<String, String> notNullParams = new HashMap<>();
        for (Map.Entry<String, String> param : requestParams.entrySet()) {
            if (!param.getValue().equals("")) {
                notNullParams.put(param.getKey(), param.getValue());
            }
        }
        String messageToSend = objectMapper.writeValueAsString(notNullParams);
        TextMessage message = session.createTextMessage(messageToSend);
        message.setStringProperty("Request name", "Get user list");

        producer.send(destination, message);
    }

    public void sendCreateUserMessage(User userToSave) throws JMSException, JsonProcessingException {
        System.out.println("Вызван метод sendCreateUserMessage().");
        String messageToSend = objectMapper.writeValueAsString(userToSave);
        TextMessage message = session.createTextMessage(messageToSend);
        message.setStringProperty("Request name", "Create user");

        producer.send(destination, message);
    }

    public void sendUpdateUserMessage(User updatedUser) throws JMSException, JsonProcessingException {
        System.out.println("Вызван метод sendUpdateUserMessage().");
        String messageToSend = objectMapper.writeValueAsString(updatedUser);
        TextMessage message = session.createTextMessage(messageToSend);
        message.setStringProperty("Request name", "Update user");

        producer.send(destination, message);
    }

    public void sendDeleteUserMessage(int userToDeleteId) throws JsonProcessingException, JMSException {
        System.out.println("Вызван метод sendDeleteUserMessage().");
        String messageToSend = objectMapper.writeValueAsString(userToDeleteId);
        TextMessage message = session.createTextMessage(messageToSend);
        message.setStringProperty("Request name", "Delete user");

        producer.send(destination, message);
    }
}
