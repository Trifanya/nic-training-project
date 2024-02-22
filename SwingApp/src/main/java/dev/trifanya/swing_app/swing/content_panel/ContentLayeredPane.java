package dev.trifanya.swing_app.swing.content_panel;

import dev.trifanya.swing_app.swing.content_panel.task.task_form.TaskFormPanel;
import dev.trifanya.swing_app.swing.content_panel.task.task_list.TaskListPanel;
import dev.trifanya.swing_app.swing.content_panel.task.task_page.TaskDetailsPanel;
import dev.trifanya.swing_app.swing.content_panel.user.credentials_form.CredentialsFormPanel;
import dev.trifanya.swing_app.swing.content_panel.user.user_form.UserFormPanel;
import dev.trifanya.swing_app.swing.content_panel.user.user_page.UserDetailsPanel;
import dev.trifanya.swing_app.swing.MainFrame;
import dev.trifanya.swing_app.swing.content_panel.user.user_list.UserListPanel;
import lombok.Getter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ContentLayeredPane extends JLayeredPane {
    private final MainFrame mainFrame;

    private UserListPanel userListPanel;
    private UserFormPanel userFormPanel;
    private UserDetailsPanel userDetailsPanel;

    private TaskListPanel taskListPanel;
    private TaskFormPanel taskFormPanel;
    private TaskDetailsPanel taskDetailsPanel;

    private final List<JPanel> layers = new ArrayList<>();

    private CredentialsFormPanel credentialsFormPanel;

    public ContentLayeredPane(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridBagLayout());
        setBackground(MainFrame.firstColor);
        setBorder(new LineBorder(MainFrame.secondColor, 3, true));
        setMinimumSize(new Dimension(600, 400));
    }

    public void init() {
        /**
         * USER
         */
        userListPanel = new UserListPanel();
        userListPanel.init(this);
        layers.add(userListPanel);

        userFormPanel = new UserFormPanel(this);
        userFormPanel.init();
        layers.add(userFormPanel);

        userDetailsPanel = new UserDetailsPanel();
        userDetailsPanel.init();
        layers.add(userDetailsPanel);

        credentialsFormPanel = new CredentialsFormPanel(this);
        credentialsFormPanel.init();
        layers.add(credentialsFormPanel);

        /**
         * TASK
         */
        taskListPanel = new TaskListPanel();
        taskListPanel.init(this);
        layers.add(taskListPanel);

        taskFormPanel = new TaskFormPanel();
        taskFormPanel.init(this);
        layers.add(taskFormPanel);

        taskDetailsPanel = new TaskDetailsPanel();
        taskDetailsPanel.init();
        layers.add(taskDetailsPanel);

        for (JPanel panel : layers) {
            add(panel, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            setLayer(panel, 0);
        }
        setLayer(credentialsFormPanel, 1);
    }

    public void setCurrentPanel(JPanel currentPanel) {
        // Все панели, кроме текущей, уходят на нулевой слой
        for (JPanel panel : layers) {
            setLayer(panel, 0);
        }
        setLayer(currentPanel, 1); // Текущая панель выходит на первый слой

        // Если текущей панелью является панель со списком задач, то панель сортировки и фильтров становится активной
        if (currentPanel instanceof TaskListPanel) {
            mainFrame.getSortAndFiltersPanel().setContentVisibility(true);
        } else {
            mainFrame.getSortAndFiltersPanel().setContentVisibility(false);
        }
    }
}
