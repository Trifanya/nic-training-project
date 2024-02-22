package dev.trifanya.swing_app.activemq.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.trifanya.server_app.model.User;
import dev.trifanya.swing_app.swing.MainFrame;
import dev.trifanya.swing_app.swing.menu_panel.MenuPanel;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class UserMessageHandler {
    private final ObjectMapper objectMapper;
    private final MainFrame mainFrame;

    public UserMessageHandler(MainFrame mainFrame) {
        objectMapper = new ObjectMapper();
        this.mainFrame = mainFrame;
    }

    /**
     * Метод, вызываемый в случае успешного входа в аккаунт.
     * - Меняет доступные кнопки в меню.
     * - Устанавливает текущего пользователя.
     * - Переключает интерфейс на панель с формой пользователя и заполняет ее данными текущего пользователя.
     */
    public void handleAuthUser(TextMessage textMessage) throws JMSException, JsonProcessingException {
        System.out.println("Вызван метод handleAuthUser().");
        User user = objectMapper.readValue(textMessage.getText(), User.class);
        System.out.println(mainFrame.getContentLayeredPane());
        mainFrame.getMenuPanel().changeLoginStatus();
        mainFrame.getContentLayeredPane().getCredentialsFormPanel().clearForm();
        mainFrame.getContentLayeredPane().getUserDetailsPanel().setCurrentUser(user);
        mainFrame.getContentLayeredPane().getUserDetailsPanel().fill();
        mainFrame.getContentLayeredPane().setCurrentPanel(mainFrame.getContentLayeredPane().getUserDetailsPanel());
    }

    public void handleSingleUser(TextMessage textMessage) throws JMSException, JsonProcessingException {
        System.out.println("Вызван метод handleSingleUser().");
        User user = objectMapper.readValue(textMessage.getText(), User.class);
        mainFrame.getContentLayeredPane().getUserDetailsPanel().fill(user);
        mainFrame.getContentLayeredPane().setCurrentPanel(mainFrame.getContentLayeredPane().getUserDetailsPanel());
    }

    public void handleUserList(TextMessage textMessage) throws JMSException, JsonProcessingException {
        System.out.println("Вызван метод handleUserList().");
        List<User> users = objectMapper.readValue(textMessage.getText(), ArrayList.class);
        mainFrame.getContentLayeredPane().setCurrentPanel(mainFrame.getContentLayeredPane().getUserListPanel());
    }

    public void handleSuccessfulCreation(TextMessage textMessage) throws JMSException {
        System.out.println("Вызван метод handleSuccess() в UserMessageHandler.");
        JOptionPane.showMessageDialog(mainFrame.getContentLayeredPane().getUserFormPanel(), textMessage.getText());
    }

    public void handleError(TextMessage textMessage) {
        System.out.println("Вызван метод handleError() в UserMessageHandler.");
    }
}
