package dev.trifanya.swing_app.swing.content_panel.user.user_form;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.trifanya.server_app.model.User;
import dev.trifanya.swing_app.swing.MainFrame;
import dev.trifanya.swing_app.swing.content_panel.ContentLayeredPane;
import dev.trifanya.server_app.validator.UserValidator;
import lombok.Getter;
import lombok.Setter;

import javax.jms.JMSException;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Getter
@Setter
public class UserFormPanel extends JPanel {
    private User currentUser;

    private ContentLayeredPane contentLayeredPane;

    private final int leftMargin = 25;
    private final int rightMargin = 25;
    private final int topMargin = 25;
    private final int bottomMargin = 25;

    private int currentRow = 0;
    private int currentColumn = 0;

    private int ipadx = 20;
    private int ipady = 20;

    private final int labelHorizontalAlignment = SwingConstants.LEFT;

    private JLabel pageTitleLabel;
    private JPanel formPanel;

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

    public UserFormPanel(ContentLayeredPane contentLayeredPane) {
        this.contentLayeredPane = contentLayeredPane;
        setLayout(new GridBagLayout());
        setMinimumSize(new Dimension(600, 400));
        setBackground(MainFrame.firstColor);
        setBorder(new LineBorder(MainFrame.secondColor, 3, true));
    }

    public void init() {
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
        formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        MainFrame.setBasicInterface(formPanel);
        add(formPanel, new GridBagConstraints(
                0, 1, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 0, 0));
    }

    private void initUserNameRow() {
        nameLabel = new JLabel("Имя:");
        MainFrame.setBasicInterface(nameLabel);
        nameLabel.setBorder(null);
        nameLabel.setHorizontalAlignment(labelHorizontalAlignment);
        formPanel.add(nameLabel, new GridBagConstraints(currentColumn++, currentRow, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(topMargin, leftMargin, bottomMargin, rightMargin), 10, 10));

        nameField = new JTextField();
        nameField.setColumns(30);
        MainFrame.setBasicInterface(nameField);
        nameField.setCaretColor(MainFrame.secondColor);
        nameField.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(nameField, new GridBagConstraints(currentColumn--, currentRow++, 2, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(topMargin, leftMargin, bottomMargin, rightMargin), ipadx, ipady));
    }

    private void initSurnameRow() {
        surnameLabel = new JLabel("Фамилия:");
        MainFrame.setBasicInterface(surnameLabel);
        surnameLabel.setBorder(null);
        surnameLabel.setHorizontalAlignment(labelHorizontalAlignment);
        formPanel.add(surnameLabel, new GridBagConstraints(currentColumn++, currentRow, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, leftMargin, bottomMargin, rightMargin), ipadx, ipady));

        surnameField = new JTextField();
        surnameField.setColumns(30);
        MainFrame.setBasicInterface(surnameField);
        surnameField.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(surnameField, new GridBagConstraints(currentColumn--, currentRow++, 2, 1, 1, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, leftMargin, bottomMargin, rightMargin), ipadx, ipady));
    }

    private void initPositionRow() {
        positionLabel = new JLabel("Должность:");
        MainFrame.setBasicInterface(positionLabel);
        positionLabel.setBorder(null);
        positionLabel.setHorizontalAlignment(labelHorizontalAlignment);
        formPanel.add(positionLabel, new GridBagConstraints(currentColumn++, currentRow, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, leftMargin, bottomMargin, rightMargin), ipadx, ipady));

        positionField = new JTextField();
        positionField.setColumns(30);
        MainFrame.setBasicInterface(positionField);
        positionField.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(positionField, new GridBagConstraints(currentColumn--, currentRow++, 2, 1, 1, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, leftMargin, bottomMargin, rightMargin), ipadx, ipady));
    }

    private void initEmailRow() {
        emailLabel = new JLabel("Email:");
        MainFrame.setBasicInterface(emailLabel);
        emailLabel.setBorder(null);
        emailLabel.setHorizontalAlignment(labelHorizontalAlignment);
        formPanel.add(emailLabel, new GridBagConstraints(currentColumn++, currentRow, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, leftMargin, bottomMargin, rightMargin), ipadx, ipady));

        emailField = new JTextField();
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

        passwordField = new JPasswordField();
        passwordField.setColumns(30);
        MainFrame.setBasicInterface(passwordField);
        passwordField.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(passwordField, new GridBagConstraints(currentColumn--, currentRow++, 2, 1, 1, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, leftMargin, bottomMargin, rightMargin), ipadx, ipady));
    }

    private void initPasswordConfirmationRow() {
        passwordConfirmationLabel = new JLabel("<html><p style=»text-align: right;»>Подтверждение<br>пароля:</p></html>");
        MainFrame.setBasicInterface(passwordConfirmationLabel);
        passwordConfirmationLabel.setBorder(null);
        passwordConfirmationLabel.setHorizontalAlignment(labelHorizontalAlignment);
        formPanel.add(passwordConfirmationLabel, new GridBagConstraints(currentColumn++, currentRow, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, leftMargin, bottomMargin, rightMargin), ipadx, ipady));

        passwordConfirmationField = new JPasswordField();
        passwordConfirmationField.setColumns(30);
        MainFrame.setBasicInterface(passwordConfirmationField);
        passwordConfirmationField.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(passwordConfirmationField, new GridBagConstraints(currentColumn--, currentRow++, 2, 1, 1, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, leftMargin, bottomMargin, rightMargin), ipadx, ipady));
    }

    public void initSubmitButton() {
        submitButton = new JButton("ЗАРЕГИСТРИРОВАТЬ ПОЛЬЗОВАТЕЛЯ");
        submitButton.setPreferredSize(new Dimension(400, 40));
        MainFrame.setBasicInterface(submitButton);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = new User()
                        .setName(nameField.getText())
                        .setSurname(surnameField.getText())
                        .setPosition(positionField.getText())
                        .setEmail(emailField.getText())
                        .setPassword(passwordField.getText());

                if (currentUser == null) {
                    try {
                        contentLayeredPane.getMainFrame().getUserMessageProducer().sendCreateUserMessage(user);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    clearUserForm();
                } else {
                    user.setId(currentUser.getId());
                    try {
                        contentLayeredPane.getMainFrame().getUserMessageProducer().sendUpdateUserMessage(user);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(
                            UserFormPanel.this, "Информация о пользователе успешно обновлена",
                            "Сообщение", JOptionPane.INFORMATION_MESSAGE);
                }
            }});
        add(submitButton, new GridBagConstraints(
                0, 5, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 0, 20));
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
    public void clearUserForm() {
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
            clearUserForm();
            rewriteToCreate();
            return;
        }
        fillUserForm(user);
        rewriteToUpdate();
    }
}
