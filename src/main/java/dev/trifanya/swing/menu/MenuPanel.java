package dev.trifanya.swing.menu;

import dev.trifanya.swing.MainFrame;
import dev.trifanya.swing.content.ContentLayeredPane;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Getter
@Setter
public class MenuPanel extends JPanel {
    private MainFrame mainFrame;

    private JButton taskListButton;
    private JButton newTaskButton;
    private JButton userListButton;
    private JButton newUserButton;

    public MenuPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(250, 0));
        MainFrame.setBasicInterface(this);
    }

    public void init(ContentLayeredPane contentLayeredPane) {
        taskListButtonInit();
        newTaskButtonInit();
        userListButtonInit();
        newUserButtonInit();
    }

    private void taskListButtonInit() {
        taskListButton = new JButton("Список задач");
        taskListButton.setPreferredSize(new Dimension(200, 40));
        MainFrame.setBasicInterface(taskListButton);
        taskListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.getSortAndFiltersPanel().setContentVisible(true);
                mainFrame.getContentLayeredPane().putPanelOnTop("TASK LIST");
            }
        });
        add(taskListButton, new GridBagConstraints(
                0, 0, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 15, 0), 0, 10));
    }

    private void newTaskButtonInit() {
        newTaskButton = new JButton("Создать задачу");
        newTaskButton.setPreferredSize(new Dimension(200, 40));
        MainFrame.setBasicInterface(newTaskButton);
        newTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.getSortAndFiltersPanel().setContentVisible(false);
                mainFrame.getContentLayeredPane().getTaskFormPanel().setCurrentTask(null);
                mainFrame.getContentLayeredPane().putPanelOnTop("NEW TASK");
            }
        });
        add(newTaskButton, new GridBagConstraints(
                0, 1, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 15, 0), 0, 10));
    }

    private void userListButtonInit() {
        userListButton = new JButton("Список участников");
        userListButton.setPreferredSize(new Dimension(200, 40));
        MainFrame.setBasicInterface(userListButton);
        userListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.getSortAndFiltersPanel().setContentVisible(false);
                mainFrame.getContentLayeredPane().putPanelOnTop("USER LIST");
            }
        });
        add(userListButton, new GridBagConstraints(
                0, 2, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 15, 0), 0, 10));
    }

    private void newUserButtonInit() {
        newUserButton = new JButton("Добавить участника");
        newUserButton.setPreferredSize(new Dimension(200, 40));
        MainFrame.setBasicInterface(newUserButton);
        newUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.getSortAndFiltersPanel().setContentVisible(false);
                mainFrame.getContentLayeredPane().getUserFormPanel().setCurrentUser(null);
                mainFrame.getContentLayeredPane().putPanelOnTop("NEW USER");
            }
        });
        add(newUserButton, new GridBagConstraints(
                0, 3, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 15, 0), 0, 10));
    }
}
