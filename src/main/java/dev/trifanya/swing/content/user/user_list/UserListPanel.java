package dev.trifanya.swing.content.user.user_list;

import dev.trifanya.service.UserService;
import dev.trifanya.swing.MainFrame;
import dev.trifanya.swing.content.ContentLayeredPane;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserListPanel extends JPanel implements Runnable {
    private UserService userService;
    private ContentLayeredPane contentLayeredPane;

    private JLabel userListLabel;

    private UserTableModel userTableModel;
    private UserTable userTable;
    private UserListScrollPane userListScrollPane;

    private JButton userDetailsButton;
    private JButton createUserButton;
    private JButton updateUserButton;
    private JButton deleteUserButton;

    private String sortByColumn = "id";
    private String sortDir = "ASC";

    public UserListPanel() {
        setLayout(new GridBagLayout());
        setMinimumSize(new Dimension(600, 400));
        MainFrame.setBasicInterface(this);
    }

    public void init(ContentLayeredPane contentLayeredPane) {
        userService = new UserService();

        this.contentLayeredPane = contentLayeredPane;

        initUserListLabel();
        add(userListLabel, new GridBagConstraints(
                1, 0, 2, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(25, 25, 25, 25), 30, 20));

        userTableModel = new UserTableModel();
        userTable = new UserTable(userTableModel);
        userListScrollPane = new UserListScrollPane(userTable);

        add(userListScrollPane, new GridBagConstraints(
                0, 1, 4, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 25, 25, 25), 0, 0));

        initUserDetailsButton();
        add(userDetailsButton, new GridBagConstraints(
                0, 2, 1, 1, 1, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 25, 25, 25), 0, 0));

        initCreateUserButton();
        add(createUserButton, new GridBagConstraints(
                1, 2, 1, 1, 1, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 25, 25, 25), 0, 0));

        initUpdateUserButton();
        add(updateUserButton, new GridBagConstraints(
                2, 2, 1, 1, 1, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 25, 25, 25), 0, 0));

        initDeleteUserButton();
        add(deleteUserButton, new GridBagConstraints(
                3, 2, 1, 1, 1, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 25, 25, 25), 0, 0));

        (new Thread(this)).start();
    }

    public void initUserListLabel() {
        userListLabel = new JLabel("СПИСОК УЧАСТНИКОВ");
        userListLabel.setBackground(MainFrame.firstColor);
        userListLabel.setForeground(MainFrame.secondColor);
        userListLabel.setFont(MainFrame.font);
        userListLabel.setBorder(new LineBorder(MainFrame.secondColor, 3, true));
        userListLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public void initUserDetailsButton() {
        userDetailsButton = new JButton("Подробнее");
        userDetailsButton.setBackground(MainFrame.firstColor);
        userDetailsButton.setForeground(MainFrame.secondColor);
        userDetailsButton.setFont(MainFrame.font);
        userDetailsButton.setBorder(new LineBorder(MainFrame.secondColor, 3, true));
        userDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int userId = Integer.valueOf(userTableModel.getValueAt(userTable.getSelectedRow(), 0));
                contentLayeredPane.getUserDetailsPanel().setCurrentUser(userService.getUserById(userId));
                contentLayeredPane.getUserDetailsPanel().fill();
                contentLayeredPane.putPanelOnTop("USER DETAILS");
            }
        });
    }

    public void initCreateUserButton() {
        createUserButton = new JButton("Создать");
        createUserButton.setBackground(MainFrame.firstColor);
        createUserButton.setForeground(MainFrame.secondColor);
        createUserButton.setFont(MainFrame.font);
        createUserButton.setBorder(new LineBorder(MainFrame.secondColor, 3, true));
        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentLayeredPane.getTaskFormPanel().setCurrentTask(null);
                contentLayeredPane.putPanelOnTop("NEW USER");
            }
        });
    }

    public void initUpdateUserButton() {
        updateUserButton = new JButton("Редактировать");
        updateUserButton.setBackground(MainFrame.firstColor);
        updateUserButton.setForeground(MainFrame.secondColor);
        updateUserButton.setFont(MainFrame.font);
        updateUserButton.setBorder(new LineBorder(MainFrame.secondColor, 3, true));
        updateUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentLayeredPane.putPanelOnTop("NEW USER");
                int userId = Integer.valueOf(userTableModel.getValueAt(userTable.getSelectedRow(), 0));
                contentLayeredPane.getUserFormPanel().setCurrentUser(userService.getUserById(userId));
            }
        });
    }

    public void initDeleteUserButton() {
        deleteUserButton = new JButton("Удалить");
        deleteUserButton.setBackground(MainFrame.firstColor);
        deleteUserButton.setForeground(MainFrame.secondColor);
        deleteUserButton.setFont(MainFrame.font);
        deleteUserButton.setBorder(new LineBorder(MainFrame.secondColor, 3, true));
        deleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int userId = Integer.valueOf(userTableModel.getValueAt(userTable.getSelectedRow(), 0));
                System.out.println(userId);
                userService.deletaUserById(userId);
            }
        });
    }

    @Override
    public void run() {
        while (true) {
            try {
                userTableModel.fillTable(sortByColumn, sortDir);
                this.repaint();
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
