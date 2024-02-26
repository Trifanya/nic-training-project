package dev.trifanya.server_app.activemq.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.trifanya.server_app.ServerApp;
import dev.trifanya.server_app.model.Task;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.Logger;

import javax.jms.*;
import java.util.List;

import static javax.jms.Session.AUTO_ACKNOWLEDGE;

public class TaskMessageProducer {
    private static final Logger logger = ServerApp.logger;
    private final Session session;
    private final ObjectMapper objectMapper;

    private MessageProducer producer;

    public TaskMessageProducer() throws JMSException {
        session = ServerApp.connection.createSession(false, AUTO_ACKNOWLEDGE);
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    public void sendTask(Destination responseDestination, Task taskToSend) {
        logger.trace("TaskMessageProducer: Вызван метод sendTask().");
        try {
            String messageToSend = objectMapper.writeValueAsString(taskToSend);
            TextMessage message = session.createTextMessage(messageToSend);
            message.setStringProperty("Response name", "Single task");
            producer = session.createProducer(responseDestination);
            producer.send(message);
            logger.trace("TaskMessageProducer: Сообщение успешно отправлено.");
        } catch (JMSException | JsonProcessingException exception) {
            logger.error("TaskMessageHandler: Произошла ошибка при отправке сообщения.");
        }
    }

    public void sendTaskList(Destination responseDestination, List<Task> taskListToSend) {
        logger.trace("TaskMessageProducer: Вызван метод sendTaskList().");
        try {
            String messageToSend = objectMapper.writeValueAsString(taskListToSend);
            TextMessage message = session.createTextMessage(messageToSend);
            message.setStringProperty("Response name", "Task list");
            producer = session.createProducer(responseDestination);
            producer.send(message);
            logger.trace("TaskMessageProducer: Сообщение успешно отправлено.");
        } catch (JMSException | JsonProcessingException exception) {
            logger.error("TaskMessageHandler: Произошла ошибка при отправке сообщения.");
        }
    }

    public void sendSuccessMessage(Destination responseDestination, String msg) {
        logger.trace("TaskMessageProducer: Вызван метод sendSuccessMessage().");
        try {
            TextMessage message = session.createTextMessage(msg);
            message.setStringProperty("Response name", "Task success");
            producer = session.createProducer(responseDestination);
            producer.send(message);
            logger.trace("TaskMessageProducer: Сообщение успешно отправлено.");
        } catch (JMSException exception) {
            logger.error("TaskMessageHandler: Произошла ошибка при отправке сообщения.");
        }
    }

    public void sendErrorMessage(Destination responseDestination, String msg) {
        logger.trace("TaskMessageProducer: Вызван метод sendErrorMessage().");
        try {
            TextMessage message = session.createTextMessage(msg);
            message.setStringProperty("Response name", "Task error");
            producer = session.createProducer(responseDestination);
            producer.send(message);
            logger.trace("TaskMessageProducer: Сообщение успешно отправлено.");
        } catch (JMSException exception) {
            logger.error("TaskMessageHandler: Произошла ошибка при отправке сообщения.");
        }
    }
}
