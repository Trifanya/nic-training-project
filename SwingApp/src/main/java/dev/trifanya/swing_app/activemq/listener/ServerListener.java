package dev.trifanya.swing_app.activemq.listener;

import dev.trifanya.swing_app.activemq.handler.TaskMessageHandler;
import dev.trifanya.swing_app.activemq.handler.UserMessageHandler;
import dev.trifanya.swing_app.swing.MainFrame;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class ServerListener implements MessageListener {
    private final TaskMessageHandler taskMessageHandler;
    private final UserMessageHandler userMessageHandler;

    private final MainFrame mainFrame;

    public ServerListener(MainFrame mainFrame) {
        taskMessageHandler = new TaskMessageHandler(mainFrame);
        userMessageHandler = new UserMessageHandler(mainFrame);
        this.mainFrame = mainFrame;
    }

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            String responseName = textMessage.getStringProperty("Response name");
            switch (responseName) {
                case "Single task":
                    taskMessageHandler.handleSingleTask(textMessage);
                    break;
                case "Task list":
                    taskMessageHandler.handleTaskList(textMessage);
                    break;
                case "Authenticated user":
                    userMessageHandler.handleAuthUser(textMessage);
                    break;
                case "Single user":
                    userMessageHandler.handleSingleUser(textMessage);
                    break;
                case "User list":
                    userMessageHandler.handleUserList(textMessage);
                    break;
                case "User created successfully":
                    userMessageHandler.handleSuccessfulCreation(textMessage);
                    break;
                case "Error":
                    break;
            }
        } catch (Exception exception) {
            System.out.println("Server listener: Произошла ошибка при обработке сообщения.");
            exception.printStackTrace();
        }
    }
}
