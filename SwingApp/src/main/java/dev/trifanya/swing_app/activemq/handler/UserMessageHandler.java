package dev.trifanya.swing_app.activemq.handler;

import dev.trifanya.server_app.model.User;
import dev.trifanya.swing_app.swing.MainFrame;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.ArrayList;
import javax.jms.TextMessage;
import javax.jms.JMSException;

public class UserMessageHandler {
    private final MainFrame mainFrame;
    private final ObjectMapper objectMapper;

    public UserMessageHandler(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    /**
     * Метод, вызываемый в случае успешного входа в аккаунт.
     * - Меняет доступные кнопки в меню.
     * - Устанавливает текущего пользователя.
     * - Переключает интерфейс на панель с формой пользователя и заполняет ее данными текущего пользователя.
     */
    public void handleAuth(TextMessage textMessage) {
        System.out.println("Вызван метод handleAuthUser().");
        try {
            User user = objectMapper.readValue(textMessage.getText(), User.class);
            mainFrame.signIn(user);
        } catch (JMSException | JsonProcessingException exception) {
            System.out.println("UserMessageHandler: Произошла ошибка при обработке сообщения в методе handleAuth()");
        }
    }

    public void handleList(TextMessage textMessage) {
        System.out.println("Вызван метод handleUserList().");
        try {
            List<User> users = objectMapper.readValue(textMessage.getText(), new TypeReference<ArrayList<User>>() {});
            mainFrame.setUserList(users);
        } catch (JsonProcessingException | JMSException exception) {
            System.out.println("UserMessageHandler: Произошла ошибка при обработке сообщения в методе handleList()");
        }
    }

    public void handleSuccess(TextMessage textMessage) {
        System.out.println("Вызван метод handleSuccess() в UserMessageHandler.");
        try {
            mainFrame.updateUsers(textMessage.getText());
        } catch (JMSException exception) {
            System.out.println("UserMessageHandler: Произошла ошибка при обработке сообщения в методе handleSuccess()");
        }
    }

    public void handleError(TextMessage textMessage) {
        System.out.println("Вызван метод handleError() в UserMessageHandler.");
        try {
            mainFrame.showWarning(textMessage.getText());
        } catch (JMSException exception) {
            System.out.println("UserMessageHandler: Произошла ошибка при обработке сообщения в методе handleError()");
        }
    }
}
