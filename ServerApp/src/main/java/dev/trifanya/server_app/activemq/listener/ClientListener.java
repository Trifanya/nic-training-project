package dev.trifanya.server_app.activemq.listener;

import dev.trifanya.server_app.activemq.handler.TaskMessageHandler;
import dev.trifanya.server_app.activemq.handler.UserMessageHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jms.*;

public class ClientListener implements MessageListener {
    private static final Logger logger = LogManager.getLogger(ClientListener.class);

    private final TaskMessageHandler taskMessageHandler;
    private final UserMessageHandler userMessageHandler;

    private Destination destination;

    public ClientListener() {
        this.taskMessageHandler = new TaskMessageHandler();
        this.userMessageHandler = new UserMessageHandler();
    }

    public ClientListener(Destination destination) {
        this();
        this.destination = destination;
    }

    @Override
    public void onMessage(Message message) {
        logger.trace("Вызван метод onMessage().");
        TextMessage textMessage = (TextMessage) message;
        try {
            Destination responseDestination = destination;
            if (destination == null) {
                responseDestination = message.getJMSReplyTo();
            }
            logger.trace("Response destination: " + responseDestination);

            String requestName = textMessage.getStringProperty("Request name");
            logger.trace("Request name: \"" + requestName + "\"");

            switch (requestName) {
                case "Get single task":
                    taskMessageHandler.handleGetSingleTask(responseDestination, textMessage); break;
                case "Get task list":
                    taskMessageHandler.handleGetTaskList(responseDestination, textMessage); break;
                case "Create task":
                    taskMessageHandler.handleCreateTask(responseDestination, textMessage); break;
                case "Update task":
                    taskMessageHandler.handleUpdateTask(responseDestination, textMessage); break;
                case "Delete task":
                    taskMessageHandler.handleDeleteTask(responseDestination, textMessage); break;
                case "Sign in":
                    userMessageHandler.handleSignIn(responseDestination, textMessage); break;
                case "Get single user":
                    userMessageHandler.handleGetSingleUser(responseDestination, textMessage); break;
                case "Get user list":
                    userMessageHandler.handleGetUserList(responseDestination, textMessage); break;
                case "Create user":
                    userMessageHandler.handleCreateUser(responseDestination, textMessage); break;
                case "Update user":
                    userMessageHandler.handleUpdateUser(responseDestination, textMessage); break;
                case "Delete user":
                    userMessageHandler.handleDeleteUser(responseDestination, textMessage); break;
            }
        } catch (JMSException exception) {
            logger.error("Произошла ошибка при первичной обработке сообщения.", exception);
        }
    }
}
