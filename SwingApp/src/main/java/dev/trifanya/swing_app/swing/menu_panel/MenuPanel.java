package dev.trifanya.swing_app.swing.menu_panel;

import dev.trifanya.swing_app.swing.MainFrame;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

@Getter
@Setter
public class MenuPanel extends JPanel {
    private MainFrame mainFrame;

    private JButton taskListButton;
    private JButton newTaskButton;
    private JButton userListButton;
    private JButton userDetailsButton;
    private JButton signUpButton;
    private JButton signInButton;
    private JButton signOutButton;

    private List<JButton> buttons = new ArrayList<>();

    private boolean signedIn;

    public MenuPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(250, 0));
        MainFrame.setBasicInterface(this);
    }

    public void init() {
        taskListButtonInit();
        newTaskButtonInit();
        userListButtonInit();
        userDetailsButtonInit();
        signUpButtonInit();
        signInButtonInit();
        signOutButtonInit();

        int gridy = 0;
        for (JButton button : buttons) {
            button.setPreferredSize(new Dimension(200, 40));
            MainFrame.setBasicInterface(button);
            add(button, new GridBagConstraints(
                    0, gridy++, 1, 1, 0, 0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 15, 0), 0, 10));
        }
    }

    private void taskListButtonInit() {
        taskListButton = new JButton("Список задач");
        taskListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.getContentLayeredPane().setCurrentPanel(mainFrame.getContentLayeredPane().getTaskListPanel());
            }
        });
        buttons.add(taskListButton);
        taskListButton.setVisible(false);
    }

    private void newTaskButtonInit() {
        newTaskButton = new JButton("Создать задачу");
        newTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.getSortAndFiltersPanel().setContentVisibility(false);
                mainFrame.getContentLayeredPane().getTaskFormPanel().setCurrentTask(null);
                mainFrame.getContentLayeredPane().setCurrentPanel(mainFrame.getContentLayeredPane().getTaskFormPanel());
            }
        });
        buttons.add(newTaskButton);
        newTaskButton.setVisible(false);
    }

    private void userListButtonInit() {
        userListButton = new JButton("Список участников");
        userListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.getContentLayeredPane().setCurrentPanel(mainFrame.getContentLayeredPane().getUserListPanel());
            }
        });
        buttons.add(userListButton);
        userListButton.setVisible(false);
    }

    private void userDetailsButtonInit() {
        userDetailsButton = new JButton("Профиль");
        userDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.getContentLayeredPane().getUserDetailsPanel().fill();
                mainFrame.getContentLayeredPane().setCurrentPanel(mainFrame.getContentLayeredPane().getUserDetailsPanel());
            }
        });
        buttons.add(userDetailsButton);
        userDetailsButton.setVisible(false);
    }

    private void signInButtonInit() {
        signInButton = new JButton("Вход");
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.getContentLayeredPane().setCurrentPanel(mainFrame.getContentLayeredPane().getCredentialsFormPanel());
            }
        });
        buttons.add(signInButton);
        signInButton.setVisible(true);
    }

    private void signOutButtonInit() {
        signOutButton = new JButton("Выход");
        signOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String question = "Вы уверены, что хотите выйти из аккаунта?";
                if (JOptionPane.showConfirmDialog(mainFrame.getContentLayeredPane(), question,
                        "Подтверждение", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                    return;
                }
                changeLoginStatus();
                mainFrame.getContentLayeredPane().setCurrentPanel(mainFrame.getContentLayeredPane().getCredentialsFormPanel());
            }
        });
        buttons.add(signOutButton);
        signOutButton.setVisible(false);
    }

    private void signUpButtonInit() {
        signUpButton = new JButton("Регистрация");
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.getContentLayeredPane().setCurrentPanel(mainFrame.getContentLayeredPane().getUserFormPanel());
            }
        });
        buttons.add(signUpButton);
        signUpButton.setVisible(true);
    }

    public void changeLoginStatus() {
        signedIn = !signedIn;
        for (JButton button : buttons) {
            button.setVisible(!button.isVisible());
        }
    }
}
