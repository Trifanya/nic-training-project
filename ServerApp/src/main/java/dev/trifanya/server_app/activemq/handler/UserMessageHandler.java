package dev.trifanya.server_app.activemq.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.trifanya.server_app.activemq.producer.UserMessageProducer;
import dev.trifanya.server_app.exception.NotFoundException;
import dev.trifanya.server_app.model.User;
import dev.trifanya.server_app.service.UserService;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.HashMap;
import java.util.Map;

public class UserMessageHandler {
    private final ObjectMapper objectMapper;
    private final UserService userService;
    private final UserMessageProducer userMessageProducer;

    public UserMessageHandler() throws JMSException {
        this.objectMapper = new ObjectMapper();
        this.userService = new UserService();
        this.userMessageProducer = new UserMessageProducer();
    }

    public void handleSignIn(Destination responseDestination, TextMessage textMessage) throws JMSException, JsonProcessingException {
        System.out.println(textMessage.getText());

        String[] credentials = textMessage.getText().split(" ");
        String email = credentials[0];
        String password = credentials[1];
        try {
            User user = userService.getUserByEmail(email);
            if (user.getPassword().equals(password)) {
                userMessageProducer.sendAuthUser(responseDestination, user);
                System.out.println("Аутентификация прошла успешно.");
            } else {
                userMessageProducer.sendErrorMessage(responseDestination, "Вы ввели неверный пароль.");
                System.out.println("Неверный пароль");
            }
        } catch (NotFoundException exception) {
            userMessageProducer.sendErrorMessage(responseDestination, exception.getMessage());
            System.out.println("Пользователь не найден.");
        }
    }

    public void handleGetSingleUser(Destination responseDestination, TextMessage textMessage) throws JMSException, JsonProcessingException {
        int userId = objectMapper.readValue(textMessage.getText(), Integer.class);
        userMessageProducer.sendUser(responseDestination, userService.getUserById(userId));
    }

    public void handleGetUserList(Destination responseDestination, TextMessage textMessage) throws JMSException, JsonProcessingException {
        Map<String, String> userRequestParams = objectMapper.readValue(textMessage.getText(), new TypeReference<HashMap<String, String>>() {
        });
        String userSortBy = userRequestParams.remove("sortBy");
        String userSortDir = userRequestParams.remove("sortDir");
        userMessageProducer.sendUserList(
                responseDestination,
                userService.getUsers(userRequestParams, userSortBy, userSortDir));

    }

    public void handleCreateUser(Destination responseDestination, TextMessage textMessage) throws JMSException, JsonProcessingException {
        User userToSave = objectMapper.readValue(textMessage.getText(), User.class);
        try {
            userService.createNewUser(userToSave);
            userMessageProducer.sendUserCreatedMessage(responseDestination, "Пользователь успешно зарегистрирован.");
        } catch (Exception exception) {
            userMessageProducer.sendErrorMessage(responseDestination, exception.getMessage());
        }
    }

    public void handleUpdateUser(Destination responseDestination, TextMessage textMessage) throws JMSException, JsonProcessingException {
        User updatedUser = objectMapper.readValue(textMessage.getText(), User.class);
        try {
            userService.updateUserInfo(updatedUser);
            userMessageProducer.sendUserCreatedMessage(responseDestination, "Профиль успешно отредактирован.");
        } catch (Exception exception) {
            userMessageProducer.sendErrorMessage(responseDestination, exception.getMessage());
        }
    }

    public void handleDeleteUser(Destination responseDestination, TextMessage textMessage) throws JMSException, JsonProcessingException {
        int userToDeleteId = objectMapper.readValue(textMessage.getText(), Integer.class);
        try {
            userService.deletaUserById(userToDeleteId);
            userMessageProducer.sendUserCreatedMessage(responseDestination, "Пользователь успешно удален.");
        } catch (Exception exception) {
            userMessageProducer.sendErrorMessage(responseDestination, exception.getMessage());
        }
    }
}
