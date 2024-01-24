package dev.trifanya.main_frame.menu;

import lombok.Getter;
import lombok.Setter;
import dev.trifanya.main_frame.MainFrame;
import dev.trifanya.main_frame.content.ContentLayeredPane;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Getter
@Setter
public class MenuPanel extends JPanel {
    private ContentLayeredPane contentLayeredPane;

    private JButton taskListButton;
    private JButton newTaskButton;
    private JButton newMemberButton;
    private JButton workspaceSettingsButton;
    private JButton exitButton;
    public MenuPanel() {
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(250, 0));
        MainFrame.setBasicInterface(this);
    }

    public void init(ContentLayeredPane contentLayeredPane) {
        this.contentLayeredPane = contentLayeredPane;

        mainMenuButtonInit();
        newTaskButtonInit();
        newMemberButtonInit();
        workspaceSettingsButtonInit();
    }

    private void mainMenuButtonInit() {
        taskListButton = new JButton("Список задач");
        taskListButton.setPreferredSize(new Dimension(200, 40));
        MainFrame.setBasicInterface(taskListButton);
        taskListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentLayeredPane.putPanelOnTop("TASK LIST");
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
                contentLayeredPane.getTaskFormPanel().setCurrentTask(null);
                contentLayeredPane.putPanelOnTop("NEW TASK");
            }
        });
        add(newTaskButton, new GridBagConstraints(
                0, 1, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 15, 0), 0, 10));
    }

    private void newMemberButtonInit() {
        newMemberButton = new JButton("Добавить участника");
        newMemberButton.setPreferredSize(new Dimension(200, 40));
        MainFrame.setBasicInterface(newMemberButton);
        add(newMemberButton, new GridBagConstraints(
                0, 2, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 15, 0), 0, 10));
    }

    private void workspaceSettingsButtonInit() {
        workspaceSettingsButton = new JButton("<html>Настройки рабочего<p style=\"text-align:center;\">пространства</html>");
        workspaceSettingsButton.setPreferredSize(new Dimension(200, 70));
        MainFrame.setBasicInterface(workspaceSettingsButton);
        add(workspaceSettingsButton, new GridBagConstraints(
                0, 3, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 15, 0), 0, 10));
    }
}
