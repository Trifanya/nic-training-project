package dev.trifanya.swing_app.activemq.producer;

import dev.trifanya.server_app.model.Task;
import dev.trifanya.swing_app.SwingClientApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jms.*;
import java.util.Map;
import java.util.HashMap;

import static javax.jms.Session.AUTO_ACKNOWLEDGE;

public class TaskMessageProducer {
    private static final Logger logger = LogManager.getLogger(TaskMessageProducer.class);

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
        logger.trace("Вызван метод sendGetTaskListMessage().");
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
            logger.info("Сообщение успешно отправлено.");
        } catch (JMSException | JsonProcessingException exception) {
            logger.error("Произошла ошибка при отправке сообщения.");
        }
    }

    public void sendCreateTaskMessage(Task taskToSave) {
        logger.trace("Вызван метод sendCreateTaskMessage().");
        try {
            String messageToSend = objectMapper.writeValueAsString(taskToSave);
            TextMessage message = session.createTextMessage(messageToSend);
            message.setStringProperty("Request name", "Create task");
            producer.send(destination, message);
            logger.info("Сообщение успешно отправлено.");
        } catch (JMSException | JsonProcessingException exception) {
            logger.error("Произошла ошибка при отправке сообщения.");
        }
    }

    public void sendUpdateTaskMessage(Task updatedTask) {
        logger.trace("Вызван метод sendUpdateTaskMessage().");
        try {
            String messageToSend = objectMapper.writeValueAsString(updatedTask);
            TextMessage message = session.createTextMessage(messageToSend);
            message.setStringProperty("Request name", "Update task");
            producer.send(destination, message);
            logger.info("Сообщение успешно отправлено.");
        } catch (JMSException | JsonProcessingException exception) {
            logger.error("Произошла ошибка при отправке сообщения.");
        }
    }

    public void sendDeleteTaskMessage(int taskToDeleteId) {
        logger.trace("Вызван метод sendDeleteTaskMessage().");
        try {
            String messageToSend = objectMapper.writeValueAsString(taskToDeleteId);
            TextMessage message = session.createTextMessage(messageToSend);
            message.setStringProperty("Request name", "Delete task");
            producer.send(destination, message);
            logger.info("Сообщение успешно отправлено.");
        } catch (JMSException | JsonProcessingException exception) {
            logger.error("Произошла ошибка при отправке сообщения.");
        }
    }
}
