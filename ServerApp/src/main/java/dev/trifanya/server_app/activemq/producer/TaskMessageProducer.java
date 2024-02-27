package dev.trifanya.server_app.activemq.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.trifanya.server_app.ServerApp;
import dev.trifanya.server_app.model.Task;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jms.*;
import java.util.List;

import static javax.jms.Session.AUTO_ACKNOWLEDGE;

public class TaskMessageProducer {
    private static final Logger logger = LogManager.getLogger(TaskMessageProducer.class);
    private Session session;
    private ObjectMapper objectMapper;

    private MessageProducer producer;

    public TaskMessageProducer() {
        try {
            session = ServerApp.connection.createSession(false, AUTO_ACKNOWLEDGE);
            objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules();
        } catch (JMSException exception) {
            logger.error("Произошла ошибка при создании сессии для работы c сервером ActiveMQ.", exception);
        }
    }

    public void sendTask(Destination responseDestination, Task taskToSend) throws JsonProcessingException, JMSException {
        logger.trace("Вызван метод sendTask().");
        String messageToSend = objectMapper.writeValueAsString(taskToSend);
        TextMessage message = session.createTextMessage(messageToSend);
        message.setStringProperty("Response name", "Single task");
        producer = session.createProducer(responseDestination);
        producer.send(message);
        logger.trace("Сообщение успешно отправлено.");
    }

    public void sendTaskList(Destination responseDestination, List<Task> taskListToSend) throws JsonProcessingException, JMSException {
        logger.trace("Вызван метод sendTaskList().");
        String messageToSend = objectMapper.writeValueAsString(taskListToSend);
        TextMessage message = session.createTextMessage(messageToSend);
        message.setStringProperty("Response name", "Task list");
        producer = session.createProducer(responseDestination);
        producer.send(message);
        logger.trace("Сообщение успешно отправлено.");
    }

    public void sendSuccessMessage(Destination responseDestination, String msg) throws JMSException {
        logger.trace("Вызван метод sendSuccessMessage().");
        TextMessage message = session.createTextMessage(msg);
        message.setStringProperty("Response name", "Task success");
        producer = session.createProducer(responseDestination);
        producer.send(message);
        logger.trace("Сообщение успешно отправлено.");
    }

    public void sendErrorMessage(Destination responseDestination, String msg) {
        logger.trace("Вызван метод sendErrorMessage().");
        try {
            TextMessage message = session.createTextMessage(msg);
            message.setStringProperty("Response name", "Task error");
            producer = session.createProducer(responseDestination);
            producer.send(message);
            logger.trace("Сообщение успешно отправлено.");
        } catch (JMSException exception) {
            logger.error("Не удалось отправить клиенту сообщение об ошибке.", exception);
        }
    }
}
