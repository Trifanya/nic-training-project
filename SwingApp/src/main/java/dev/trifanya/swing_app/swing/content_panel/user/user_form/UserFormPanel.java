package dev.trifanya.swing_app.swing.content_panel.user.user_form;

import dev.trifanya.server_app.model.User;
import dev.trifanya.swing_app.swing.MainFrame;
import dev.trifanya.swing_app.swing.content_panel.ContentLayeredPane;

import lombok.Getter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.LinkedHashMap;

@Getter
public class UserFormPanel extends JPanel {
    private User currentUser;
    private final ContentLayeredPane contentLayeredPane;

    private JLabel pageTitleLabel;
    private JPanel formPanel;
    private JButton submitButton;

    private JLabel nameLabel;
    private JLabel surnameLabel;
    private JLabel positionLabel;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JLabel passwordConfirmationLabel;

    private JTextField nameField;
    private JTextField surnameField;
    private JTextField positionField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField passwordConfirmationField;

    private Map<JLabel, JTextField> formLines = new LinkedHashMap<>();

    private final int leftMargin = 25;
    private final int rightMargin = 25;
    private final int topMargin = 25;
    private final int bottomMargin = 25;

    private int currentRow = 0;
    private int currentColumn = 0;

    private int ipadx = 10;
    private int ipady = 10;

    private final int labelHorizontalAlignment = SwingConstants.LEFT;

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

        for (Map.Entry<JLabel, JTextField> formLine : formLines.entrySet()) {
            MainFrame.setBasicInterface(formLine.getKey());
            formLine.getKey().setBorder(null);
            formLine.getKey().setHorizontalAlignment(labelHorizontalAlignment);
            formPanel.add(formLine.getKey(), new GridBagConstraints(
                    currentColumn++, currentRow, 1, 1, 0, 0,
                    GridBagConstraints.EAST, GridBagConstraints.NONE,
                    new Insets(topMargin, leftMargin, bottomMargin, 0), ipadx, ipady));
            formLine.getValue().setColumns(30);
            MainFrame.setBasicInterface(formLine.getValue());
            formLine.getValue().setHorizontalAlignment(SwingConstants.CENTER);
            formPanel.add(formLine.getValue(), new GridBagConstraints(
                    currentColumn--, currentRow++, 1, 1, 0, 0,
                    GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                    new Insets(15, leftMargin, 15, rightMargin), ipadx, ipady * 2));
        }
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

    public void initSubmitButton() {
        submitButton = new JButton("ЗАРЕГИСТРИРОВАТЬ ПОЛЬЗОВАТЕЛЯ");
        submitButton.setPreferredSize(new Dimension(400, 40));
        MainFrame.setBasicInterface(submitButton);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!passwordField.getText().equals(passwordConfirmationField.getText())) {
                    JOptionPane.showMessageDialog(contentLayeredPane, "Пароли не совпадают",
                            "Предупреждение", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                User user = new User()
                        .setName(nameField.getText())
                        .setSurname(surnameField.getText())
                        .setPosition(positionField.getText())
                        .setEmail(emailField.getText())
                        .setPassword(passwordField.getText());
                if (currentUser == null) {
                    contentLayeredPane.getMainFrame().getUserMessageProducer().sendCreateUserMessage(user);
                } else {
                    user.setId(currentUser.getId());
                    contentLayeredPane.getMainFrame().getUserMessageProducer().sendUpdateUserMessage(user);
                }
            }
        });
        add(submitButton, new GridBagConstraints(
                0, 5, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 0, 20));
    }

    private void initUserNameRow() {
        nameLabel = new JLabel("Имя:");
        nameField = new JTextField();
        formLines.put(nameLabel, nameField);
    }

    private void initSurnameRow() {
        surnameLabel = new JLabel("Фамилия:");
        surnameField = new JTextField();
        formLines.put(surnameLabel, surnameField);
    }

    private void initPositionRow() {
        positionLabel = new JLabel("Должность:");
        positionField = new JTextField();
        formLines.put(positionLabel, positionField);
    }

    private void initEmailRow() {
        emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        formLines.put(emailLabel, emailField);
    }

    private void initPasswordRow() {
        passwordLabel = new JLabel("Пароль:");
        passwordField = new JPasswordField();
        formLines.put(passwordLabel, passwordField);
    }

    private void initPasswordConfirmationRow() {
        passwordConfirmationLabel = new JLabel("<html><p style=»text-align: right;»>Подтверждение<br>пароля:</p></html>");
        passwordConfirmationField = new JPasswordField();
        formLines.put(passwordConfirmationLabel, passwordConfirmationField);
    }

    /** Заполнение формы данными редактируемой задачи */
    public void fillUserForm(User user) {
        nameField.setText(user.getName());
        surnameField.setText(user.getSurname());
        positionField.setText(user.getPosition());
        emailField.setText(user.getEmail());
        passwordField.setText(user.getPassword());
    }

    /** Очистка формы от данных */
    public void clearUserForm() {
        nameField.setText("");
        surnameField.setText("");
        positionField.setText("");
        emailField.setText("");
        passwordField.setText("");
        passwordConfirmationField.setText("");
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
