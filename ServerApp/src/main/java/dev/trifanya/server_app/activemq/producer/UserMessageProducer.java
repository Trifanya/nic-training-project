package dev.trifanya.server_app.activemq.producer;

import dev.trifanya.server_app.ServerApp;
import dev.trifanya.server_app.model.User;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jms.*;
import java.util.List;

import static javax.jms.Session.AUTO_ACKNOWLEDGE;

public class UserMessageProducer {
    private static Logger logger = LogManager.getLogger(UserMessageProducer.class);
    private Session session;
    private ObjectMapper objectMapper;

    private MessageProducer producer;

    public UserMessageProducer() {
        try {
            session = ServerApp.connection.createSession(false, AUTO_ACKNOWLEDGE);
            objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules();
        } catch (JMSException exception) {
            logger.error("Произошла ошибка при создании сессии для работы c сервером ActiveMQ.", exception);
        }
    }

    public void sendAuthUser(Destination responseDestination, User userToSend) throws JsonProcessingException, JMSException {
        logger.trace("Вызван метод sendAuthUser().");
        String messageToSend = objectMapper.writeValueAsString(userToSend);
        TextMessage message = session.createTextMessage(messageToSend);
        message.setStringProperty("Response name", "Authenticated user");
        producer = session.createProducer(responseDestination);
        producer.send(message);
        logger.trace("Сообщение успешно отправлено.");
    }

    public void sendUser(Destination responseDestination, User userToSend) throws JsonProcessingException, JMSException {
        logger.trace("Вызван метод sendUser().");
        String messageToSend = objectMapper.writeValueAsString(userToSend);
        TextMessage message = session.createTextMessage(messageToSend);
        message.setStringProperty("Response name", "Single user");
        producer = session.createProducer(responseDestination);
        producer.send(message);
        logger.trace("Сообщение успешно отправлено.");
    }

    public void sendUserList(Destination responseDestination, List<User> userListToSend) throws JMSException, JsonProcessingException {
        logger.trace("Вызван метод sendUserList().");
        String messageToSend = objectMapper.writeValueAsString(userListToSend);
        TextMessage message = session.createTextMessage(messageToSend);
        message.setStringProperty("Response name", "User list");
        producer = session.createProducer(responseDestination);
        producer.send(message);
        logger.trace("Сообщение успешно отправлено.");
    }

    public void sendUserCreatedMessage(Destination responseDestination, String msg) throws JMSException {
        logger.trace("Вызван метод sendUserCreatedMessage().");
        TextMessage message = session.createTextMessage(msg);
        message.setStringProperty("Response name", "User success");
        producer = session.createProducer(responseDestination);
        producer.send(message);
        logger.trace("Сообщение успешно отправлено.");
    }

    public void sendErrorMessage(Destination responseDestination, String msg) {
        logger.trace("Вызван метод sendErrorMessage().");
        try {
            TextMessage message = session.createTextMessage(msg);
            message.setStringProperty("Response name", "User error");
            producer = session.createProducer(responseDestination);
            producer.send(message);
            logger.trace("Сообщение успешно отправлено.");
        } catch (JMSException exception) {
            logger.error("Не удалось отправить клиенту сообщение об ошибке.", exception);
        }

    }
}
