package dev.trifanya.swing_app.swing.content_panel.user.credentials_form;

import dev.trifanya.swing_app.swing.MainFrame;
import dev.trifanya.swing_app.swing.content_panel.ContentLayeredPane;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;

public class CredentialsFormPanel extends JPanel {
    private final ContentLayeredPane contentLayeredPane;

    private JLabel pageTitleLabel;
    private JPanel formPanel;
    private JButton submitButton;

    private JLabel emailLabel;
    private JLabel passwordLabel;

    private JTextField emailField;
    private JPasswordField passwordField;

    private final Map<JLabel, JTextField> formLines = new LinkedHashMap<>();

    private final int leftMargin = 25;
    private final int rightMargin = 25;
    private final int topMargin = 25;
    private final int bottomMargin = 25;

    private int ipadx = 10;
    private int ipady = 20;

    public CredentialsFormPanel(ContentLayeredPane contentLayeredPane) {
        this.contentLayeredPane = contentLayeredPane;
        setLayout(new GridBagLayout());
        setMinimumSize(new Dimension(600, 400));
        setBackground(MainFrame.firstColor);
        setBorder(new LineBorder(MainFrame.secondColor, 3, true));
    }

    public void init() {
        initPageTitleLabel();
        initFormPanel();
        initSubmitButton();

        emailLabel = new JLabel("Email:");
        passwordLabel = new JLabel("Пароль:");

        emailField = new JTextField("");
        passwordField = new JPasswordField("");

        formLines.put(emailLabel, emailField);
        formLines.put(passwordLabel, passwordField);

        int currentRow = 0;
        int currentColumn = 0;
        for (Map.Entry<JLabel, JTextField> formLine : formLines.entrySet()) {
            MainFrame.setBasicInterface(formLine.getKey());
            formLine.getKey().setBorder(null);
            formLine.getKey().setHorizontalAlignment(SwingConstants.LEFT);
            formPanel.add(formLine.getKey(), new GridBagConstraints(currentColumn++, currentRow, 1, 1, 0, 0,
                    GridBagConstraints.EAST, GridBagConstraints.NONE,
                    new Insets(0, leftMargin, bottomMargin, rightMargin), ipadx, ipady));
            formLine.getValue().setColumns(30);
            MainFrame.setBasicInterface(formLine.getValue());
            formLine.getValue().setHorizontalAlignment(SwingConstants.CENTER);
            formPanel.add(formLine.getValue(), new GridBagConstraints(
                    currentColumn--, currentRow++, 2, 1, 1, 0,
                    GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                    new Insets(0, leftMargin, bottomMargin, rightMargin), ipadx, ipady));
        }
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

    public void initSubmitButton() {
        submitButton = new JButton("ВОЙТИ В АККАУНТ");
        MainFrame.setBasicInterface(submitButton);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    contentLayeredPane.getMainFrame().getUserMessageProducer()
                            .sendSignInMessage(emailField.getText(), passwordField.getText());
            }
        });
        add(submitButton, new GridBagConstraints(
                0, 2, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 50, 30));
    }

    public void clearForm() {
        emailField.setText("");
        passwordField.setText("");
    }
}
