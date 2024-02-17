package dev.trifanya.swing_crudapp.swing.content_panel.user.credentials_form;

import dev.trifanya.swing_crudapp.model.User;
import dev.trifanya.swing_crudapp.service.UserService;
import dev.trifanya.swing_crudapp.swing.MainFrame;
import dev.trifanya.swing_crudapp.swing.content_panel.ContentLayeredPane;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CredentialsFormPanel extends JPanel {
    private final int leftMargin = 25;
    private final int rightMargin = 25;
    private final int topMargin = 25;
    private final int bottomMargin = 25;

    private int currentRow = 0;
    private int currentColumn = 0;

    private int ipadx = 20;
    private int ipady = 20;

    private final int labelHorizontalAlignment = SwingConstants.LEFT;

    private UserService userService;
    private ContentLayeredPane contentLayeredPane;

    private JLabel pageTitleLabel;
    private JPanel formPanel;

    private JLabel emailLabel;
    private JTextField emailField;

    private JLabel passwordLabel;
    private JPasswordField passwordField;

    private JButton submitButton;

    public CredentialsFormPanel(ContentLayeredPane contentLayeredPane) {
        this.contentLayeredPane = contentLayeredPane;
        setLayout(new GridBagLayout());
        setMinimumSize(new Dimension(600, 400));
        setBackground(MainFrame.firstColor);
        setBorder(new LineBorder(MainFrame.secondColor, 3, true));
    }

    public void init() {
        userService = new UserService();
        initPageTitleLabel();
        initFormPanel();
        initEmailRow();
        initPasswordRow();
        initSubmitButton();
    }

    public void initPageTitleLabel() {
        pageTitleLabel = new JLabel("ВХОД В АККАУНТ");
        MainFrame.setBasicInterface(pageTitleLabel);
        pageTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(pageTitleLabel, new GridBagConstraints(
                0, 0, 1, 1, 0, 0,
                GridBagConstraints.NORTH, GridBagConstraints.NONE,
                new Insets(topMargin, leftMargin, bottomMargin, rightMargin), 30, 20));
    }

    public void initFormPanel() {
        formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        MainFrame.setBasicInterface(formPanel);
        add(formPanel, new GridBagConstraints(
                0, 1, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 0, 0));

    }

    private void initEmailRow() {
        emailLabel = new JLabel("Email:");
        MainFrame.setBasicInterface(emailLabel);
        emailLabel.setBorder(null);
        emailLabel.setHorizontalAlignment(labelHorizontalAlignment);
        formPanel.add(emailLabel, new GridBagConstraints(currentColumn++, currentRow, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, leftMargin, bottomMargin, rightMargin), ipadx, ipady));

        emailField = new JTextField("");
        emailField.setColumns(30);
        MainFrame.setBasicInterface(emailField);
        emailField.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(emailField, new GridBagConstraints(currentColumn--, currentRow++, 2, 1, 1, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, leftMargin, bottomMargin, rightMargin), ipadx, ipady));
    }

    private void initPasswordRow() {
        passwordLabel = new JLabel("Пароль:");
        MainFrame.setBasicInterface(passwordLabel);
        passwordLabel.setBorder(null);
        passwordLabel.setHorizontalAlignment(labelHorizontalAlignment);
        formPanel.add(passwordLabel, new GridBagConstraints(currentColumn++, currentRow, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, leftMargin, bottomMargin, rightMargin), ipadx, ipady));

        passwordField = new JPasswordField("");
        passwordField.setColumns(30);
        MainFrame.setBasicInterface(passwordField);
        passwordField.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(passwordField, new GridBagConstraints(
                currentColumn--, currentRow++, 2, 1, 1, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, leftMargin, bottomMargin, rightMargin), ipadx, ipady));
    }

    public void initSubmitButton() {
        submitButton = new JButton("ВОЙТИ В АККАУНТ");
        submitButton.setPreferredSize(new Dimension(240, 40));
        MainFrame.setBasicInterface(submitButton);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = userService.getUserByEmail(emailField.getText());
                if (user == null) {
                    JOptionPane.showMessageDialog(
                            CredentialsFormPanel.this,
                            "Пользователь с указанным email не зарегистрирован",
                            "Предупреждение",
                            JOptionPane.WARNING_MESSAGE);
                    clearForm();
                    return;
                }
                if (user.getPassword().equals(passwordField.getText())) {
                    contentLayeredPane.getMainFrame().getMenuPanel().signIn();
                    contentLayeredPane.putPanelOnTop("USER DETAILS");
                    contentLayeredPane.getUserDetailsPanel().setCurrentUser(user);
                    contentLayeredPane.getUserDetailsPanel().fill();
                } else {
                    JOptionPane.showMessageDialog(
                            CredentialsFormPanel.this,
                            "Вы ввели неверный пароль",
                            "Предупреждение",
                            JOptionPane.WARNING_MESSAGE);
                }
                clearForm();
            }
        });
        add(submitButton, new GridBagConstraints(
                0, 2, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 0, 20));
    }

    public void clearForm() {
        emailField.setText("");
        passwordField.setText("");
    }


}
