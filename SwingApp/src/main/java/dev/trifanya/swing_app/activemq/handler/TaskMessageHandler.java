package dev.trifanya.swing_app.activemq.handler;

import dev.trifanya.server_app.model.Task;
import dev.trifanya.swing_app.swing.MainFrame;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.ArrayList;
import javax.jms.TextMessage;
import javax.jms.JMSException;

public class TaskMessageHandler {
    private final MainFrame mainFrame;
    private final ObjectMapper objectMapper;

    public TaskMessageHandler(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    public void handleList(TextMessage textMessage) {
        System.out.println("Вызван метод handleTaskList().");
        try {
            List<Task> tasks = objectMapper.readValue(textMessage.getText(), new TypeReference<ArrayList<Task>>() {});
            mainFrame.setTaskList(tasks);
        } catch (JsonProcessingException | JMSException exception) {
            System.out.println("TaskMessageHandler: Произошла ошибка при обработке сообщения в методе handleList()");
        }
    }

    public void handleSuccess(TextMessage textMessage) {
        System.out.println("Вызван метод handleSuccess() в TaskMessageHandler.");
        try {
            mainFrame.updateTasks(textMessage.getText());
        } catch (JMSException exception) {
            System.out.println("TaskMessageHandler: Произошла ошибка при обработке сообщения в методе handleSuccess()");
        }
    }

    public void handleError(TextMessage textMessage) {
        System.out.println("Вызван метод handleError() в TaskMessageHandler.");
        try {
            mainFrame.showWarning(textMessage.getText());
        } catch (JMSException exception) {
            System.out.println("TaskMessageHandler: Произошла ошибка при обработке сообщения в методе handleError()");
        }
    }
}
