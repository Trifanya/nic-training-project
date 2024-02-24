package dev.trifanya.swing_app.swing.content_panel.user.user_page;

import dev.trifanya.server_app.model.User;
import dev.trifanya.swing_app.swing.MainFrame;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.util.Map;
import java.util.LinkedHashMap;

@Getter
@Setter
public class UserDetailsPanel extends JPanel {
    private User currentUser;

    private JLabel pageTitleLabel;
    private JPanel userInfoPanel;

    private JLabel idLabel;
    private JLabel nameLabel;
    private JLabel surnameLabel;
    private JLabel positionLabel;
    private JLabel emailLabel;

    private JLabel idValueLabel;
    private JLabel nameValueLabel;
    private JLabel surnameValueLabel;
    private JLabel positionValueLabel;
    private JLabel emailValueLabel;

    Map<JLabel, JLabel> panelLines = new LinkedHashMap<>();

    private final int leftMargin = 15;
    private final int rightMargin = 15;
    private final int topMargin = 10;
    private final int bottomMargin = 10;

    private final int labelGridWidth = 1;
    private final int labelGridHeight = 1;
    private final int valueGridWidth = 1;
    private final int valueGridHeight = 1;

    private final double labelWeightX = 0;
    private final double labelWeightY = 0;
    private final double valueWeightX = 1;
    private final double valueWeightY = 0;

    private final int ipadx = 10;
    private final int ipady = 15;

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

        idLabel = new JLabel("ID:");
        nameLabel = new JLabel("Имя:");
        surnameLabel = new JLabel("Фамилия:");
        positionLabel = new JLabel("Должность:");
        emailLabel = new JLabel("Email:");

        idValueLabel = new JLabel("");
        nameValueLabel = new JLabel("");
        surnameValueLabel = new JLabel("");
        positionValueLabel = new JLabel("");
        emailValueLabel = new JLabel("");

        panelLines.put(idLabel, idValueLabel);
        panelLines.put(nameLabel, nameValueLabel);
        panelLines.put(surnameLabel, surnameValueLabel);
        panelLines.put(positionLabel, positionValueLabel);
        panelLines.put(emailLabel, emailValueLabel);

        int currentRow = 0;
        int currentColumn = 0;
        for (Map.Entry<JLabel, JLabel> panelLine : panelLines.entrySet()) {
            MainFrame.setBasicInterface(panelLine.getKey());
            panelLine.getKey().setBorder(null);
            panelLine.getKey().setHorizontalAlignment(labelHorizontalAlignment);
            userInfoPanel.add(panelLine.getKey(), new GridBagConstraints(
                    currentColumn++, currentRow, labelGridWidth, labelGridHeight, labelWeightX, labelWeightY,
                    GridBagConstraints.EAST, GridBagConstraints.NONE,
                    new Insets(topMargin, leftMargin, bottomMargin, rightMargin), ipadx, ipady));
            MainFrame.setBasicInterface(panelLine.getValue());
            panelLine.getValue().setHorizontalAlignment(valueHorizontalAlignment);
            userInfoPanel.add(panelLine.getValue(), new GridBagConstraints(
                    currentColumn--, currentRow++, valueGridWidth, valueGridHeight, valueWeightX, valueWeightY,
                    GridBagConstraints.WEST, GridBagConstraints.BOTH,
                    new Insets(topMargin, leftMargin, bottomMargin, rightMargin), ipadx, ipady));
        }
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

    public void fill() {
        idValueLabel.setText(String.valueOf(currentUser.getId()));
        nameValueLabel.setText(currentUser.getName());
        surnameValueLabel.setText(currentUser.getSurname());
        positionValueLabel.setText(currentUser.getPosition());
        emailValueLabel.setText(currentUser.getEmail());
    }

    public void fill(User user) {
        idValueLabel.setText(String.valueOf(user.getId()));
        nameValueLabel.setText(user.getName());
        surnameValueLabel.setText(user.getSurname());
        positionValueLabel.setText(user.getPosition());
        emailValueLabel.setText(user.getEmail());
    }
}
