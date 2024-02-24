package dev.trifanya.server_app.activemq.consumer;

import dev.trifanya.server_app.activemq.handler.TaskMessageHandler;
import dev.trifanya.server_app.activemq.handler.UserMessageHandler;

import javax.jms.*;

public class ClientListener implements MessageListener {
    private final TaskMessageHandler taskMessageHandler;
    private final UserMessageHandler userMessageHandler;
    private Destination destination;

    public ClientListener() throws JMSException {
        this.taskMessageHandler = new TaskMessageHandler();
        this.userMessageHandler = new UserMessageHandler();
    }

    public ClientListener(Destination destination)throws JMSException  {
        this();
        this.destination = destination;
    }

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            Destination responseDestination;
            if (destination == null) {
                responseDestination = message.getJMSReplyTo();
            } else {
                responseDestination = destination;
            }

            String requestName = textMessage.getStringProperty("Request name");
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
            System.out.println("ClientListener: Произошла ошибка при первичной обработке сообщения.");
            exception.printStackTrace();
        }
    }
}
