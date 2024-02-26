package dev.trifanya.server_app.activemq.producer;

import dev.trifanya.server_app.ServerApp;
import dev.trifanya.server_app.model.User;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.Logger;

import javax.jms.*;
import java.util.List;

import static javax.jms.Session.AUTO_ACKNOWLEDGE;

public class UserMessageProducer {
    private static Logger logger = ServerApp.logger;
    private final Session session;
    private final ObjectMapper objectMapper;

    private MessageProducer producer;

    public UserMessageProducer() throws JMSException {
        session = ServerApp.connection.createSession(false, AUTO_ACKNOWLEDGE);
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    public void sendAuthUser(Destination responseDestination, User userToSend) {
        logger.trace("UserMessageProducer: Вызван метод sendAuthUser().");
        try {
            String messageToSend = objectMapper.writeValueAsString(userToSend);
            TextMessage message = session.createTextMessage(messageToSend);
            message.setStringProperty("Response name", "Authenticated user");
            producer = session.createProducer(responseDestination);
            producer.send(message);
            logger.trace("UserMessageProducer: Сообщение успешно отправлено.");
        } catch (JMSException | JsonProcessingException exception) {
            logger.error("UserMessageProducer: Произошла ошибка при отправке сообщения.");
        }
    }

    public void sendUser(Destination responseDestination, User userToSend) {
        logger.trace("UserMessageProducer: Вызван метод sendUser().");
        try {
            String messageToSend = objectMapper.writeValueAsString(userToSend);
            TextMessage message = session.createTextMessage(messageToSend);
            message.setStringProperty("Response name", "Single user");
            producer = session.createProducer(responseDestination);
            producer.send(message);
            logger.trace("UserMessageProducer: Сообщение успешно отправлено.");
        } catch (JMSException | JsonProcessingException exception) {
            logger.error("UserMessageProducer: Произошла ошибка при отправке сообщения.");
        }
    }

    public void sendUserList(Destination responseDestination, List<User> userListToSend) {
        logger.trace("UserMessageProducer: Вызван метод sendUserList().");
        try {
            String messageToSend = objectMapper.writeValueAsString(userListToSend);
            TextMessage message = session.createTextMessage(messageToSend);
            message.setStringProperty("Response name", "User list");
            producer = session.createProducer(responseDestination);
            producer.send(message);
            logger.trace("UserMessageProducer: Сообщение успешно отправлено.");
        } catch (JMSException | JsonProcessingException exception) {
            logger.error("UserMessageProducer: Произошла ошибка при отправке сообщения.");
        }
    }

    public void sendUserCreatedMessage(Destination responseDestination, String msg) {
        logger.trace("UserMessageProducer: Вызван метод sendUserCreatedMessage().");
        try {
            TextMessage message = session.createTextMessage(msg);
            message.setStringProperty("Response name", "User success");
            producer = session.createProducer(responseDestination);
            producer.send(message);
            logger.trace("UserMessageProducer: Сообщение успешно отправлено.");
        } catch (JMSException exception) {
            logger.error("UserMessageProducer: Произошла ошибка при отправке сообщения.");
        }
    }

    public void sendErrorMessage(Destination responseDestination, String msg) {
        logger.trace("UserMessageProducer: Вызван метод sendErrorMessage().");
        try {
            TextMessage message = session.createTextMessage(msg);
            message.setStringProperty("Response name", "User error");
            producer = session.createProducer(responseDestination);
            producer.send(message);
            logger.trace("UserMessageProducer: Сообщение успешно отправлено.");
        } catch (JMSException exception) {
            logger.error("UserMessageProducer: Произошла ошибка при отправке сообщения.");
        }
    }
}
