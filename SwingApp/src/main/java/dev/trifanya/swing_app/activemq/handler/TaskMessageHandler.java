package dev.trifanya.swing_app.activemq.handler;

import dev.trifanya.swing_app.swing.MainFrame;

import javax.jms.TextMessage;

public class TaskMessageHandler {
    private final MainFrame mainFrame;

    public TaskMessageHandler(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void handleSingleTask(TextMessage textMessage) {
        System.out.println("Вызван метод handleSingleTask().");
    }

    public void handleTaskList(TextMessage textMessage) {
        System.out.println("Вызван метод handleTaskList().");
    }

    public void handleSuccess(TextMessage textMessage) {
        System.out.println("Вызван метод handleSuccess() в TaskMessageHandler.");

    }

    public void handleError(TextMessage textMessage) {
        System.out.println("Вызван метод handleError() в TaskMessageHandler.");

    }
}
