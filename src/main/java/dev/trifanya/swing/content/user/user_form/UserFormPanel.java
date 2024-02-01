package dev.trifanya.swing.content.user.user_form;

import dev.trifanya.model.User;
import dev.trifanya.service.UserService;
import dev.trifanya.swing.MainFrame;
import dev.trifanya.swing.content.ContentLayeredPane;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserFormPanel extends JPanel {
    private UserService userService;

    private User currentUser;

    private ContentLayeredPane contentLayeredPane;

    private final int leftMargin = 25;
    private final int rightMargin = 25;
    private final int topMargin = 25;
    private final int bottomMargin = 25;

    private int currentRow = 0;
    private int currentColumn = 0;

    private final int labelHorizontalAlignment = SwingConstants.LEFT;

    private JLabel pageTitleLabel;
    private JPanel userFormPanel;

    private JLabel nameLabel;
    private JTextField nameField;

    private JLabel surnameLabel;
    private JTextField surnameField;

    private JLabel positionLabel;
    private JTextField positionField;

    private JLabel emailLabel;
    private JTextField emailField;

    private JLabel passwordLabel;
    private JPasswordField passwordField;

    private JLabel passwordConfirmationLabel;
    private JPasswordField passwordConfirmationField;

    private JButton submitButton;

    public UserFormPanel() {
        setLayout(new GridBagLayout());
        setMinimumSize(new Dimension(600, 400));
        setBackground(MainFrame.firstColor);
        setBorder(new LineBorder(MainFrame.secondColor, 3, true));
    }

    public void init(ContentLayeredPane contentLayeredPane) {
        userService = new UserService();

        this.contentLayeredPane = contentLayeredPane;

        initPageTitleLabel();
        initUserFormPanel();
        initUserNameRow();
        initSurnameRow();
        initPositionRow();
        initEmailRow();
        initPasswordRow();
        initPasswordConfirmationRow();
        initSubmitButton();
    }

    public void initPageTitleLabel() {
        pageTitleLabel = new JLabel("РЕГИСТРАЦИЯ ПОЛЬЗОВАТЕЛЯ");
        MainFrame.setBasicInterface(pageTitleLabel);
        pageTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(pageTitleLabel, new GridBagConstraints(
                0, 0, 1, 1, 0, 0,
                GridBagConstraints.NORTH, GridBagConstraints.NONE,
                new Insets(topMargin, leftMargin, bottomMargin, rightMargin), 30, 20));
    }

    public void initUserFormPanel() {
        userFormPanel = new JPanel();
        userFormPanel.setLayout(new GridBagLayout());
        MainFrame.setBasicInterface(userFormPanel);
        add(userFormPanel, new GridBagConstraints(
                0, 1, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 25, 25, 25), 0, 0));
    }

    private void initUserNameRow() {
        nameLabel = new JLabel("Имя:");
        MainFrame.setBasicInterface(nameLabel);
        nameLabel.setHorizontalAlignment(labelHorizontalAlignment);
        userFormPanel.add(nameLabel, new GridBagConstraints(currentColumn++, currentRow, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(topMargin, leftMargin, bottomMargin, rightMargin), 10, 10));

        nameField = new JTextField();
        nameField.setColumns(30);
        MainFrame.setBasicInterface(nameField);
        nameField.setCaretColor(MainFrame.secondColor);
        nameField.setHorizontalAlignment(SwingConstants.LEFT);
        userFormPanel.add(nameField, new GridBagConstraints(currentColumn--, currentRow++, 2, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(topMargin, leftMargin, bottomMargin, rightMargin), 10, 10));
    }

    private void initSurnameRow() {
        surnameLabel = new JLabel("Фамилия:");
        MainFrame.setBasicInterface(surnameLabel);
        surnameLabel.setHorizontalAlignment(labelHorizontalAlignment);
        userFormPanel.add(surnameLabel, new GridBagConstraints(currentColumn++, currentRow, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 10, 10));

        surnameField = new JTextField();
        surnameField.setColumns(30);
        MainFrame.setBasicInterface(surnameField);
        userFormPanel.add(surnameField, new GridBagConstraints(currentColumn--, currentRow++, 2, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 10, 10));
    }

    private void initPositionRow() {
        positionLabel = new JLabel("Должность:");
        MainFrame.setBasicInterface(positionLabel);
        positionLabel.setHorizontalAlignment(labelHorizontalAlignment);
        userFormPanel.add(positionLabel, new GridBagConstraints(currentColumn++, currentRow, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 10, 10));

        positionField = new JTextField();
        positionField.setColumns(30);
        MainFrame.setBasicInterface(positionField);
        userFormPanel.add(positionField, new GridBagConstraints(currentColumn--, currentRow++, 1, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 10, 10));
    }

    private void initEmailRow() {
        emailLabel = new JLabel("Email");
        MainFrame.setBasicInterface(emailLabel);
        emailLabel.setHorizontalAlignment(labelHorizontalAlignment);
        userFormPanel.add(emailLabel, new GridBagConstraints(currentColumn++, currentRow, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 10, 10));

        emailField = new JTextField();
        emailField.setColumns(30);
        MainFrame.setBasicInterface(emailField);
        userFormPanel.add(emailField, new GridBagConstraints(currentColumn--, currentRow++, 2, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 10, 10));
    }

    private void initPasswordRow() {
        passwordLabel = new JLabel("Пароль");
        MainFrame.setBasicInterface(passwordLabel);
        passwordLabel.setHorizontalAlignment(labelHorizontalAlignment);
        userFormPanel.add(passwordLabel, new GridBagConstraints(currentColumn++, currentRow, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 10, 10));

        passwordField = new JPasswordField();
        passwordField.setColumns(30);
        MainFrame.setBasicInterface(passwordField);
        userFormPanel.add(passwordField, new GridBagConstraints(currentColumn--, currentRow++, 2, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 10, 10));
    }

    private void initPasswordConfirmationRow() {
        passwordConfirmationLabel = new JLabel("Подтверждение пароля:");
        MainFrame.setBasicInterface(passwordConfirmationLabel);
        passwordConfirmationLabel.setHorizontalAlignment(labelHorizontalAlignment);
        userFormPanel.add(passwordConfirmationLabel, new GridBagConstraints(currentColumn++, currentRow, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 10, 10));

        passwordConfirmationField = new JPasswordField();
        passwordConfirmationField.setColumns(30);
        MainFrame.setBasicInterface(passwordConfirmationField);
        userFormPanel.add(passwordConfirmationField, new GridBagConstraints(currentColumn--, currentRow++, 2, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 10, 10));
    }

    public void initSubmitButton() {
        submitButton = new JButton("ЗАРЕГИСТРИРОВАТЬ ПОЛЬЗОВАТЕЛЯ");
        submitButton.setPreferredSize(new Dimension(340, 40));
        MainFrame.setBasicInterface(submitButton);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!passwordField.getText().equals(passwordConfirmationField.getText())) {
                    JPopupMenu popupMenu = new JPopupMenu("Пароли не совпадают");
                    UserFormPanel.this.add(popupMenu);
                    popupMenu.setVisible(true);
                }
                User userToSave = new User()
                        .setName(nameField.getText())
                        .setSurname(surnameField.getText())
                        .setPosition(positionField.getText())
                        .setEmail(emailField.getText())
                        .setPassword(passwordField.getText());

                if (currentUser == null) {
                    userService.createNewUser(userToSave);
                } else {
                    userToSave.setId(currentUser.getId());
                    userService.updateUserInfo(userToSave);
                }
            }
        });
        add(submitButton, new GridBagConstraints(
                0, 5, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 50, 20));
    }

    /**
     * Заполнение формы данными редактируемой задачи
     */
    public void fillUserForm(User user) {
        nameField.setText(user.getName());
        surnameField.setText(user.getSurname());
        positionField.setText(user.getPosition());
        emailField.setText(user.getEmail());
        passwordField.setText(user.getPassword());
    }

    /**
     * Очистка формы от данных
     */
    public void clearTaskForm() {
        nameField.setText("");
        surnameField.setText("");
        positionField.setText("");
        emailField.setText("");
    }

    public void rewriteToUpdate() {
        pageTitleLabel.setText("ИЗМЕНЕНИЕ ПРОФИЛЯ");
        submitButton.setText("СОХРАНИТЬ ИЗМЕНЕНИЯ");
    }

    public void rewriteToCreate() {
        pageTitleLabel.setText("РЕГИСТРАЦИЯ ПОЛЬЗОВАТЕЛЯ");
        submitButton.setText("ЗАРЕГИСТРИОВАТЬ ПОЛЬЗОВАТЕЛЯ");
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
        if (user == null) {
            clearTaskForm();
            rewriteToCreate();
            return;
        }
        fillUserForm(user);
        rewriteToUpdate();
    }
}
