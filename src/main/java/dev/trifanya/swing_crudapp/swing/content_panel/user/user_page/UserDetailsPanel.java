package dev.trifanya.swing_crudapp.swing.content_panel.user.user_page;

import dev.trifanya.swing_crudapp.model.User;
import dev.trifanya.swing_crudapp.swing.MainFrame;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

@Getter
@Setter
public class UserDetailsPanel extends JPanel {
    private User currentUser;

    private JLabel pageTitleLabel;

    private JPanel userInfoPanel;

    private JLabel idLabel;
    private JLabel userId;

    private JLabel nameLabel;
    private JLabel userName;

    private JLabel surnameLabel;
    private JLabel userSurname;

    private JLabel positionLabel;
    private JLabel userPosition;

    private JLabel emailLabel;
    private JLabel userEmail;

    private final int leftMargin = 25;
    private final int rightMargin = 25;
    private final int topMargin = 25;
    private final int bottomMargin = 25;

    private int currentRow = 0;
    private int currentColumn = 0;

    private double labelWeightX = 0;
    private double valueWeightX = 1;

    private final int labelHorizontalAlignment = SwingConstants.LEFT;
    private final int valueHorizontalAlignment = SwingConstants.CENTER;

    public UserDetailsPanel() {
        setLayout(new GridBagLayout());
        setMinimumSize(new Dimension(600, 400));
        setBackground(MainFrame.firstColor);
        setBorder(new LineBorder(MainFrame.secondColor, 3, true));
    }

    public void init() {
        initPageTitleLabel();
        initUserInfoPanel();
        initIdRow();
        initNameRow();
        initSurnameRow();
        initPositionRow();
        initEmailRow();
    }

    private void initPageTitleLabel() {
        pageTitleLabel = new JLabel("ИНФОРМАЦИЯ О ПОЛЬЗОВАТЕЛЕ");
        MainFrame.setBasicInterface(pageTitleLabel);
        pageTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(pageTitleLabel, new GridBagConstraints(
                0, 0, 1, 1, 0, 0,
                GridBagConstraints.NORTH, GridBagConstraints.NONE,
                new Insets(topMargin, leftMargin, bottomMargin, rightMargin), 30, 20));
    }

    private void initUserInfoPanel() {
        userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new GridBagLayout());
        MainFrame.setBasicInterface(userInfoPanel);
        add(userInfoPanel, new GridBagConstraints(
                0, 1, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 25, 25, 25), 0, 0));
    }

    private void initIdRow() {
        idLabel = new JLabel("ID:");
        MainFrame.setBasicInterface(idLabel);
        idLabel.setBorder(null);
        idLabel.setHorizontalAlignment(labelHorizontalAlignment);
        userInfoPanel.add(idLabel, new GridBagConstraints(
                currentColumn++, currentRow, 1, 1, labelWeightX, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(topMargin, leftMargin, bottomMargin, rightMargin), 10, 10));

        userId = new JLabel("");
        MainFrame.setBasicInterface(userId);
        userId.setHorizontalAlignment(valueHorizontalAlignment);
        userInfoPanel.add(userId, new GridBagConstraints(
                currentColumn--, currentRow++, 2, 1, valueWeightX, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(topMargin, leftMargin, bottomMargin, rightMargin), 10, 10));
    }

    private void initNameRow() {
        nameLabel = new JLabel("Имя:");
        MainFrame.setBasicInterface(nameLabel);
        nameLabel.setBorder(null);
        nameLabel.setHorizontalAlignment(labelHorizontalAlignment);
        userInfoPanel.add(nameLabel, new GridBagConstraints(
                currentColumn++, currentRow, 1, 1, labelWeightX, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 10, 10));

        userName = new JLabel("");
        MainFrame.setBasicInterface(userName);
        userName.setHorizontalAlignment(valueHorizontalAlignment);
        userInfoPanel.add(userName, new GridBagConstraints(
                currentColumn--, currentRow++, 2, 1, valueWeightX, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 10, 10));
    }

    private void initSurnameRow() {
        surnameLabel = new JLabel("Фамилия:");
        MainFrame.setBasicInterface(surnameLabel);
        surnameLabel.setBorder(null);
        surnameLabel.setHorizontalAlignment(labelHorizontalAlignment);
        userInfoPanel.add(surnameLabel, new GridBagConstraints(
                currentColumn++, currentRow, 1, 1, labelWeightX, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 10, 10));

        userSurname = new JLabel("");
        MainFrame.setBasicInterface(userSurname);
        userSurname.setHorizontalAlignment(valueHorizontalAlignment);
        userInfoPanel.add(userSurname, new GridBagConstraints(
                currentColumn--, currentRow++, 2, 1, valueWeightX, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 10, 10));
    }

    private void initPositionRow() {
        positionLabel = new JLabel("Должность:");
        MainFrame.setBasicInterface(positionLabel);
        positionLabel.setBorder(null);
        positionLabel.setHorizontalAlignment(labelHorizontalAlignment);
        userInfoPanel.add(positionLabel, new GridBagConstraints(
                currentColumn++, currentRow, 1, 1, labelWeightX, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 10, 10));

        userPosition = new JLabel("");
        MainFrame.setBasicInterface(userPosition);
        userPosition.setHorizontalAlignment(valueHorizontalAlignment);
        userInfoPanel.add(userPosition, new GridBagConstraints(
                currentColumn--, currentRow++, 2, 1, valueWeightX, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 10, 10));
    }

    private void initEmailRow() {
        emailLabel = new JLabel("Email:");
        MainFrame.setBasicInterface(emailLabel);
        emailLabel.setBorder(null);
        emailLabel.setHorizontalAlignment(labelHorizontalAlignment);
        userInfoPanel.add(emailLabel, new GridBagConstraints(
                currentColumn++, currentRow, 1, 1, labelWeightX, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 10, 10));

        userEmail = new JLabel("");
        MainFrame.setBasicInterface(userEmail);
        userEmail.setHorizontalAlignment(valueHorizontalAlignment);
        userInfoPanel.add(userEmail, new GridBagConstraints(
                currentColumn--, currentRow++, 2, 1, valueWeightX, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 10, 10));
    }

    public void fill() {
        userId.setText(String.valueOf(currentUser.getId()));
        userName.setText(currentUser.getName());
        userSurname.setText(currentUser.getSurname());
        userPosition.setText(currentUser.getPosition());
        userEmail.setText(currentUser.getEmail());
    }

    public void fill(User user) {
        userId.setText(String.valueOf(user.getId()));
        userName.setText(user.getName());
        userSurname.setText(user.getSurname());
        userPosition.setText(user.getPosition());
        userEmail.setText(user.getEmail());
    }
}
