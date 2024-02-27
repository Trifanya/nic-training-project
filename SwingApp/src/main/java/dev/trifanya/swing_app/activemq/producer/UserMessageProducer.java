package dev.trifanya.swing_app.activemq.producer;

import dev.trifanya.server_app.model.User;
import dev.trifanya.swing_app.SwingClientApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jms.*;
import java.util.Collections;

import static javax.jms.Session.AUTO_ACKNOWLEDGE;

public class UserMessageProducer {
    private static final Logger logger = LogManager.getLogger(UserMessageProducer.class);

    private final Session session;
    private final Destination destination;
    private final MessageProducer producer;
    private final ObjectMapper objectMapper;

    public UserMessageProducer() throws JMSException {
        session = SwingClientApp.connection.createSession(false, AUTO_ACKNOWLEDGE);
        destination = session.createQueue(SwingClientApp.REQUEST_FROM_SWING_CLIENT_QUEUE);
        producer = session.createProducer(destination);
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    public void sendSignInMessage(String email, String password) {
        logger.trace("Вызван метод sendSignInMessage().");
        try {
            TextMessage message = session.createTextMessage(email + " " + password);
            message.setStringProperty("Request name", "Sign in");
            producer.send(destination, message);
            logger.info("Сообщение успешно отправлено.");
        } catch (JMSException exception) {
            logger.error("Произошла ошибка при отправке сообщения.");
        }
    }

    public void sendGetUserListMessage() {
        logger.trace("Вызван метод sendGetUserListMessage().");
        try {
            String messageToSend = objectMapper.writeValueAsString(Collections.emptyMap());
            TextMessage message = session.createTextMessage(messageToSend);
            message.setStringProperty("Request name", "Get user list");
            producer.send(destination, message);
            logger.info("Сообщение успешно отправлено.");
        } catch (JMSException | JsonProcessingException exception) {
            logger.error("Произошла ошибка при отправке сообщения.");
        }
    }

    public void sendCreateUserMessage(User userToSave) {
        logger.trace("Вызван метод sendCreateUserMessage().");
        try {
            String messageToSend = objectMapper.writeValueAsString(userToSave);
            TextMessage message = session.createTextMessage(messageToSend);
            message.setStringProperty("Request name", "Create user");
            producer.send(destination, message);
            logger.info("Сообщение успешно отправлено.");
        } catch (JMSException | JsonProcessingException exception) {
            logger.error("Произошла ошибка при отправке сообщения.");
        }
    }

    public void sendUpdateUserMessage(User updatedUser) {
        logger.trace("Вызван метод sendUpdateUserMessage().");
        try {
            String messageToSend = objectMapper.writeValueAsString(updatedUser);
            TextMessage message = session.createTextMessage(messageToSend);
            message.setStringProperty("Request name", "Update user");
            producer.send(destination, message);
            logger.info("Сообщение успешно отправлено.");
        } catch (JMSException | JsonProcessingException exception) {
            logger.error("Произошла ошибка при отправке сообщения.");
        }
    }

    public void sendDeleteUserMessage(int userToDeleteId) {
        logger.trace("Вызван метод sendDeleteUserMessage().");
        try {
            String messageToSend = objectMapper.writeValueAsString(userToDeleteId);
            TextMessage message = session.createTextMessage(messageToSend);
            message.setStringProperty("Request name", "Delete user");
            producer.send(destination, message);
            logger.info("Сообщение успешно отправлено.");
        } catch (JMSException | JsonProcessingException exception) {
            logger.error("Произошла ошибка при отправке сообщения.");
        }
    }
}
