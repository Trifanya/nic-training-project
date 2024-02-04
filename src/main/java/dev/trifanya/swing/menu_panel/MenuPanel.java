package dev.trifanya.swing.menu_panel;

import dev.trifanya.swing.MainFrame;
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
    private JButton userDetailsButton;
    private JButton signUpButton;
    private JButton signInButton;
    private JButton signOutButton;

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
        taskListButton.setVisible(false);
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
        newTaskButton.setVisible(false);
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
        userListButton.setVisible(false);
    }

    private void userDetailsButtonInit() {
        userDetailsButton = new JButton("Профиль");
        userDetailsButton.setPreferredSize(new Dimension(200, 40));
        MainFrame.setBasicInterface(userDetailsButton);
        userDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.getContentLayeredPane().getUserDetailsPanel().fill();
                mainFrame.getContentLayeredPane().putPanelOnTop("USER DETAILS");
            }
        });
        add(userDetailsButton, new GridBagConstraints(
                0, 3, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 15, 0), 0, 10));
        userDetailsButton.setVisible(false);
    }

    private void signInButtonInit() {
        signInButton = new JButton("Вход");
        signInButton.setPreferredSize(new Dimension(200, 40));
        MainFrame.setBasicInterface(signInButton);
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.getContentLayeredPane().putPanelOnTop("CREDENTIALS FORM");
            }
        });
        add(signInButton, new GridBagConstraints(
                0, 4, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 15, 0), 0, 10));
        signInButton.setVisible(true);
    }

    private void signOutButtonInit() {
        signOutButton = new JButton("Выход");
        signOutButton.setPreferredSize(new Dimension(200, 40));
        MainFrame.setBasicInterface(signOutButton);
        signOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String question = "Вы уверены, что хотите выйти из аккаунта?";
                if (JOptionPane.showConfirmDialog(MenuPanel.this, question, "Подтверждение", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                    return;
                }
                signOut();
                mainFrame.getContentLayeredPane().putPanelOnTop("CREDENTIALS FORM");
            }
        });
        add(signOutButton, new GridBagConstraints(
                0, 4, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 15, 0), 0, 10));
        signOutButton.setVisible(false);
    }

    private void signUpButtonInit() {
        signUpButton = new JButton("Регистрация");
        signUpButton.setPreferredSize(new Dimension(200, 40));
        MainFrame.setBasicInterface(signUpButton);
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.getContentLayeredPane().putPanelOnTop("NEW USER");
            }
        });
        add(signUpButton, new GridBagConstraints(
                0, 5, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 15, 0), 0, 10));
        signUpButton.setVisible(true);
    }

    public void signIn() {
        signedIn = true;
        taskListButton.setVisible(true);
        newTaskButton.setVisible(true);
        userListButton.setVisible(true);
        userDetailsButton.setVisible(true);
        signInButton.setVisible(false);
        signOutButton.setVisible(true);
        signUpButton.setVisible(false);
    }
    public void signOut() {
        signedIn = false;
        taskListButton.setVisible(false);
        newTaskButton.setVisible(false);
        userListButton.setVisible(false);
        userDetailsButton.setVisible(false);
        signInButton.setVisible(true);
        signOutButton.setVisible(false);
        signUpButton.setVisible(true);
    }

}
