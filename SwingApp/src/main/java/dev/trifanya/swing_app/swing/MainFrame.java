package dev.trifanya.swing_app.swing;

import dev.trifanya.server_app.model.Task;
import dev.trifanya.server_app.model.User;
import dev.trifanya.swing_app.activemq.producer.TaskMessageProducer;
import dev.trifanya.swing_app.activemq.producer.UserMessageProducer;
import dev.trifanya.swing_app.swing.content_panel.ContentLayeredPane;
import dev.trifanya.swing_app.swing.menu_panel.MenuPanel;
import dev.trifanya.swing_app.swing.sort_and_filters_panel.SortAndFiltersPanel;

import lombok.Getter;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.jms.JMSException;
import java.util.List;

@Getter
public class MainFrame extends JFrame {
    public static int frameWidth = 1400;
    public static int frameHeight = 800;
    public static int frameMinWidth = 1000;
    public static int frameMinHeight = 600;
    public static Color firstColor = Color.WHITE;
    public static Color secondColor = Color.BLACK;
    public static Font font = new Font(Font.SANS_SERIF, Font.BOLD, 16);
    public static Border border = new LineBorder(MainFrame.secondColor, 3, true);

    private TaskMessageProducer taskMessageProducer;
    private UserMessageProducer userMessageProducer;

    private MenuPanel menuPanel;
    private ContentLayeredPane contentLayeredPane;
    private SortAndFiltersPanel sortAndFiltersPanel;

    public MainFrame() {
        setLocation(350, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(frameWidth, frameHeight));
        setMinimumSize(new Dimension(frameMinWidth, frameMinHeight));
        getContentPane().setBackground(firstColor);
    }

    public void init() throws JMSException, JsonProcessingException, InterruptedException {
        taskMessageProducer = new TaskMessageProducer();
        userMessageProducer = new UserMessageProducer();

        menuPanel = new MenuPanel(this);
        contentLayeredPane = new ContentLayeredPane(this);
        sortAndFiltersPanel = new SortAndFiltersPanel(this);

        menuPanel.init();
        contentLayeredPane.init();
        sortAndFiltersPanel.init(contentLayeredPane);

        add(menuPanel, BorderLayout.WEST);
        add(contentLayeredPane, BorderLayout.CENTER);
        add(sortAndFiltersPanel, BorderLayout.EAST);

        sortAndFiltersPanel.setContentVisibility(false);

        setVisible(true);

        userMessageProducer.sendGetUserListMessage();
        taskMessageProducer.sendGetTaskListMessage(sortAndFiltersPanel.getFilters());
    }

    public static void setBasicInterface(JComponent component) {
        component.setBackground(firstColor);
        component.setForeground(secondColor);
        component.setFont(font);
        component.setBorder(border);
    }

    public void signIn(User user) {
        menuPanel.changeLoginStatus();
        contentLayeredPane.getCredentialsFormPanel().clearForm();
        contentLayeredPane.getUserDetailsPanel().setCurrentUser(user);
        contentLayeredPane.getUserDetailsPanel().fill();
        contentLayeredPane.setCurrentPanel(contentLayeredPane.getUserDetailsPanel());
    }

    public void setUserList(List<User> users) {
        contentLayeredPane.getUserListPanel().getUserTableModel().setUserList(users, contentLayeredPane);
    }

    public void setTaskList(List<Task> tasks) {
        contentLayeredPane.getTaskListPanel().getTaskTableModel().setTaskList(tasks, contentLayeredPane);
    }

    public void updateUsers(String message) {
        JOptionPane.showMessageDialog(contentLayeredPane, message);
        contentLayeredPane.getUserFormPanel().clearUserForm();
        taskMessageProducer.sendGetTaskListMessage(sortAndFiltersPanel.getFilters());
    }

    public void updateTasks(String message) {
        JOptionPane.showMessageDialog(contentLayeredPane.getTaskFormPanel(), message);
        contentLayeredPane.getTaskFormPanel().clearTaskForm();
        taskMessageProducer.sendGetTaskListMessage(sortAndFiltersPanel.getFilters());
    }

    public void showWarning(String message) {
        JOptionPane.showMessageDialog(contentLayeredPane, message, "Предупреждение", JOptionPane.WARNING_MESSAGE);
    }
}
