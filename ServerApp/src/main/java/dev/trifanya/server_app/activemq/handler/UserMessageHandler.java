package dev.trifanya.server_app.activemq.handler;

import dev.trifanya.server_app.model.User;
import dev.trifanya.server_app.service.UserService;
import dev.trifanya.server_app.activemq.producer.UserMessageProducer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;
import java.util.HashMap;
import javax.jms.TextMessage;
import javax.jms.Destination;
import javax.jms.JMSException;

public class UserMessageHandler {
    private static final Logger logger = LogManager.getLogger(UserMessageHandler.class);

    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final UserMessageProducer userMessageProducer;

    public UserMessageHandler() {
        userService = new UserService();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        userMessageProducer = new UserMessageProducer();
    }

    public void handleSignIn(Destination responseDestination, TextMessage textMessage) {
        logger.trace("Вызван метод handleSignIn().");
        try {
            String[] credentials = objectMapper.readValue(textMessage.getText(), String[].class);
            if (credentials[0].isEmpty() || credentials[1].isEmpty()) {
                userMessageProducer.sendErrorMessage(responseDestination, "Вы не ввели логин или пароль.");
                return;
            }
            User user = userService.getUserByEmail(credentials[0]);
            if (user.getPassword().equals(credentials[1])) {
                userMessageProducer.sendAuthUser(responseDestination, user);
            } else {
                userMessageProducer.sendErrorMessage(responseDestination, "Вы ввели неверный пароль.");
            }
        } catch (JMSException exception) {
            logger.error("Произошла ошибка при обработке или отправке сообщения.", exception);
        } catch (Exception exception) {
            logger.warn("Произошла ошибка при попытке войти в аккаунт.", exception);
            userMessageProducer.sendErrorMessage(responseDestination, exception.getMessage());
        }
    }

    public void handleGetSingleUser(Destination responseDestination, TextMessage textMessage) {
        logger.trace("Вызван метод handleGetSingleUser().");
        try {
            int userId = objectMapper.readValue(textMessage.getText(), Integer.class);
            userMessageProducer.sendUser(responseDestination, userService.getUserById(userId));
        } catch (JMSException | JsonProcessingException exception) {
            logger.error("Произошла ошибка при обработке или отправке сообщения.", exception);
            userMessageProducer.sendErrorMessage(responseDestination, "Не удалось получить данные о пользователе.");
        }
    }

    public void handleGetUserList(Destination responseDestination, TextMessage textMessage) {
        logger.trace("Вызван метод handleGetUserList().");
        try {
            Map<String, String> userRequestParams = objectMapper.readValue(textMessage.getText(),
                    new TypeReference<HashMap<String, String>>() {
                    });
            userMessageProducer.sendUserList(responseDestination, userService.getUsers(userRequestParams));
        } catch (JMSException | JsonProcessingException exception) {
            logger.error("Произошла ошибка при обработке или отправке сообщения.", exception);
            userMessageProducer.sendErrorMessage(responseDestination, "Не удалось получить список пользователей.");
        }
    }

    public void handleCreateUser(Destination responseDestination, TextMessage textMessage) {
        logger.trace("Вызван метод handleCreateUser().");
        try {
            User userToSave = objectMapper.readValue(textMessage.getText(), User.class);
            userService.createNewUser(userToSave);
            userMessageProducer.sendUserCreatedMessage(responseDestination, "Пользователь успешно зарегистрирован.");
        } catch (JMSException | JsonProcessingException exception) {
            logger.error("Произошла ошибка при обработке или отправке сообщения.", exception);
            userMessageProducer.sendErrorMessage(responseDestination, "Не удалось создать пользователя.");
        } catch (Exception exception) {
            logger.warn("Произошла ошибка при попытке создать пользвателя.", exception);
            userMessageProducer.sendErrorMessage(responseDestination, exception.getMessage());
        }
    }

    public void handleUpdateUser(Destination responseDestination, TextMessage textMessage) {
        logger.trace("Вызван метод handleUpdateUser().");
        try {
            User updatedUser = objectMapper.readValue(textMessage.getText(), User.class);
            userService.updateUserInfo(updatedUser);
            userMessageProducer.sendUserCreatedMessage(responseDestination, "Профиль успешно отредактирован.");
        } catch (JMSException | JsonProcessingException exception) {
            logger.error("Произошла ошибка при обработке или отправке сообщения.", exception);
            userMessageProducer.sendErrorMessage(responseDestination, "Не удалось обновить данные пользователя.");
        } catch (Exception exception) {
            logger.warn("Произошла ошибка при попытке обновить данные пользователя.", exception);
            userMessageProducer.sendErrorMessage(responseDestination, exception.getMessage());
        }
    }

    public void handleDeleteUser(Destination responseDestination, TextMessage textMessage) {
        logger.trace("Вызван метод handleDeleteUser().");
        try {
            int userToDeleteId = objectMapper.readValue(textMessage.getText(), Integer.class);
            userService.deleteUserById(userToDeleteId);
            userMessageProducer.sendUserCreatedMessage(responseDestination, "Пользователь успешно удален.");
        } catch (JMSException | JsonProcessingException exception) {
            logger.error("Произошла ошибка при обработке или отправке сообщения.", exception);
            userMessageProducer.sendErrorMessage(responseDestination, "Не удалось удалить пользователя.");
        } catch (Exception exception) {
            logger.warn("Произошла ошибка при попытке удалить пользователя.", exception);
            userMessageProducer.sendErrorMessage(responseDestination, exception.getMessage());
        }
    }
}
