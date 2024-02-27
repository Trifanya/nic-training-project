package dev.trifanya.swing_app.activemq.handler;

import dev.trifanya.server_app.model.User;
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

public class UserMessageHandler {
    private static final Logger logger = LogManager.getLogger(UserMessageHandler.class);

    private final MainFrame mainFrame;
    private final ObjectMapper objectMapper;

    public UserMessageHandler(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    public void handleAuth(TextMessage textMessage) {
        logger.trace("Вызван метод handleAuthUser().");
        try {
            User user = objectMapper.readValue(textMessage.getText(), User.class);
            mainFrame.signIn(user);
        } catch (JMSException | JsonProcessingException exception) {
            logger.error("Произошла ошибка при обработке сообщения.");
        }
    }

    public void handleList(TextMessage textMessage) {
        logger.trace("Вызван метод handleUserList().");
        try {
            List<User> users = objectMapper.readValue(textMessage.getText(), new TypeReference<ArrayList<User>>() {});
            mainFrame.setUserList(users);
        } catch (JsonProcessingException | JMSException exception) {
            logger.error("Произошла ошибка при обработке сообщения.");
        }
    }

    public void handleSuccess(TextMessage textMessage) {
        logger.trace("Вызван метод handleSuccess().");
        try {
            mainFrame.updateUsers(textMessage.getText());
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
