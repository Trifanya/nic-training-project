package dev.trifanya.swing_app.activemq.handler;

import dev.trifanya.server_app.model.Task;
import dev.trifanya.swing_app.SwingClientApp;
import dev.trifanya.swing_app.swing.MainFrame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.ArrayList;
import javax.jms.TextMessage;
import javax.jms.JMSException;

public class TaskMessageHandler {
    private static final Logger logger = LogManager.getLogger(TaskMessageHandler.class);

    private final MainFrame mainFrame;
    private final ObjectMapper objectMapper;

    public TaskMessageHandler(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    public void handleList(TextMessage textMessage) {
        logger.trace("Вызван метод handleTaskList().");
        try {
            List<Task> tasks = objectMapper.readValue(textMessage.getText(), new TypeReference<ArrayList<Task>>() {});
            mainFrame.setTaskList(tasks);
        } catch (JsonProcessingException | JMSException exception) {
            logger.error("Произошла ошибка при обработке сообщения.");
        }
    }

    public void handleSuccess(TextMessage textMessage) {
        logger.trace("Вызван метод handleSuccess().");
        try {
            mainFrame.updateTasks(textMessage.getText());
        } catch (JMSException exception) {
            logger.error("Произошла ошибка при обработке сообщения.");
        }
    }

    public void handleError(TextMessage textMessage) {
        logger.trace("Вызван метод handleError().");
        try {
            mainFrame.showWarning(textMessage.getText());
        } catch (JMSException exception) {
            logger.error("Произошла ошибка при обработке сообщения.");
        }
    }
}
