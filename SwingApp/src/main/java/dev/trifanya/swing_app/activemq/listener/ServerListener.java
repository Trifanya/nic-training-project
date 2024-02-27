package dev.trifanya.swing_app.activemq.listener;

import dev.trifanya.swing_app.SwingClientApp;
import dev.trifanya.swing_app.swing.MainFrame;
import dev.trifanya.swing_app.activemq.handler.TaskMessageHandler;
import dev.trifanya.swing_app.activemq.handler.UserMessageHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jms.Message;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.jms.MessageListener;

public class ServerListener implements MessageListener {
    private static final Logger logger = LogManager.getLogger(ServerListener.class);
    private final TaskMessageHandler taskMessageHandler;
    private final UserMessageHandler userMessageHandler;

    public ServerListener(MainFrame mainFrame) {
        taskMessageHandler = new TaskMessageHandler(mainFrame);
        userMessageHandler = new UserMessageHandler(mainFrame);
    }

    @Override
    public void onMessage(Message message) {
        logger.trace("Вызван метод onMessage()");

        TextMessage textMessage = (TextMessage) message;
        try {
            String responseName = textMessage.getStringProperty("Response name");
            logger.trace("Response name: \"" + responseName + "\"");

            switch (responseName) {
                case "Task list":
                    taskMessageHandler.handleList(textMessage);
                    break;
                case "Task success":
                    taskMessageHandler.handleSuccess(textMessage);
                    break;
                case "Task error":
                    taskMessageHandler.handleError(textMessage);
                    break;
                case "Authenticated user":
                    userMessageHandler.handleAuth(textMessage);
                    break;
                case "User list":
                    userMessageHandler.handleList(textMessage);
                    break;
                case "User success":
                    userMessageHandler.handleSuccess(textMessage);
                    break;
                case "User error":
                    userMessageHandler.handleError(textMessage);
                    break;
                case "Wrong password":
                    userMessageHandler.handleError(textMessage);
                    break;
            }
        } catch (JMSException exception) {
            logger.error("Server listener: Произошла ошибка при обработке сообщения.");
            exception.printStackTrace();
        }
    }
}
