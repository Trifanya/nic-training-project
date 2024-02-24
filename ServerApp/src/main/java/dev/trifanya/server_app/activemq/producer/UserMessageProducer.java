package dev.trifanya.server_app.activemq.producer;

import dev.trifanya.server_app.ServerApp;
import dev.trifanya.server_app.model.User;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.jms.*;
import java.util.List;

import static javax.jms.Session.AUTO_ACKNOWLEDGE;

public class UserMessageProducer {
    private final Session session;
    private final ObjectMapper objectMapper;

    private MessageProducer producer;

    public UserMessageProducer() throws JMSException {
        session = ServerApp.connection.createSession(false, AUTO_ACKNOWLEDGE);
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    public void sendAuthUser(Destination responseDestination, User userToSend) {
        try {
            String messageToSend = objectMapper.writeValueAsString(userToSend);
            TextMessage message = session.createTextMessage(messageToSend);
            message.setStringProperty("Response name", "Authenticated user");
            producer = session.createProducer(responseDestination);
            producer.send(message);
        } catch (JMSException | JsonProcessingException exception) {
            System.out.println("UserMessageProducer: Произошла ошибка при отправке сообщения в методе sendAuthUser()");
        }
    }

    public void sendUser(Destination responseDestination, User userToSend) {
        try {
            String messageToSend = objectMapper.writeValueAsString(userToSend);
            TextMessage message = session.createTextMessage(messageToSend);
            message.setStringProperty("Response name", "Single user");
            producer = session.createProducer(responseDestination);
            producer.send(message);
        } catch (JMSException | JsonProcessingException exception) {
            System.out.println("UserMessageProducer: Произошла ошибка при отправке сообщения в методе sendUser()");
        }
    }

    public void sendUserList(Destination responseDestination, List<User> userListToSend) {
        try {
            String messageToSend = objectMapper.writeValueAsString(userListToSend);
            TextMessage message = session.createTextMessage(messageToSend);
            message.setStringProperty("Response name", "User list");
            producer = session.createProducer(responseDestination);
            producer.send(message);
        } catch (JMSException | JsonProcessingException exception) {
            System.out.println("UserMessageProducer: Произошла ошибка при отправке сообщения в методе sendUserList()");
        }
    }

    public void sendUserCreatedMessage(Destination responseDestination, String msg) {
        try {
            TextMessage message = session.createTextMessage(msg);
            message.setStringProperty("Response name", "User success");
            producer = session.createProducer(responseDestination);
            producer.send(message);
        } catch (JMSException exception) {
            System.out.println("UserMessageProducer: Произошла ошибка при отправке сообщения в методе sendUserCreatedMessage()");
        }
    }

    public void sendUserNotCreatedMessage(Destination responseDestination, String msg) {
        try {
            TextMessage message = session.createTextMessage(msg);
            message.setStringProperty("Response name", "User error");
            producer = session.createProducer(responseDestination);
            producer.send(message);
        } catch (JMSException exception) {
            System.out.println("UserMessageProducer: Произошла ошибка при отправке сообщения в методе sendUserNotCreatedMessage()");
        }
    }

    public void sendWrongPasswordMessage(Destination responseDestination, String msg) {
        try {
            TextMessage message = session.createTextMessage(msg);
            message.setStringProperty("Response name", "Wrong password");
            producer = session.createProducer(responseDestination);
            producer.send(message);
        } catch (JMSException exception) {
            System.out.println("UserMessageProducer: Произошла ошибка при обработке сообщения в методе sendWrongPasswordMessage()");
        }
    }
}
