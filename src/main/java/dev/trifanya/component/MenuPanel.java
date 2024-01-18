package dev.trifanya.component;

import lombok.Data;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Data
public class MenuPanel extends JPanel {
    private Color firstColor = Color.BLACK;
    private Color secondColor = Color.ORANGE;
    private Font font = new Font(Font.SANS_SERIF, Font.BOLD, 16);
    
    private JButton mainMenuButton = new JButton();
    private JButton newTaskButton = new JButton();
    private JButton newMemberButton = new JButton();
    private JButton workspaceSettingsButton = new JButton();
    private JButton exitButton = new JButton();

    private ContentLayeredPane contentPane;

    public MenuPanel() {
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(250, 0));
        setBackground(firstColor);
        setBorder(new LineBorder(secondColor, 3, true));
        init();
    }
    
    private void init() {
        mainMenuButtonInit();
        newTaskButtonInit();
        newMemberButtonInit();
        workspaceSettingsButtonInit();
    }

    private void mainMenuButtonInit() {
        mainMenuButton.setText("Главное меню");
        mainMenuButton.setPreferredSize(new Dimension(200, 40));
        mainMenuButton.setBackground(firstColor);
        mainMenuButton.setForeground(secondColor);
        mainMenuButton.setBorder(new LineBorder(secondColor, 3, true));
        mainMenuButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPane.putPanelOnTop("TASK LIST");
            }
        });
        this.add(mainMenuButton, new GridBagConstraints(
                0, 0, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 15, 0), 0, 10));
    }

    private void newTaskButtonInit() {
        newTaskButton.setText("Создать задачу");
        newTaskButton.setPreferredSize(new Dimension(200, 40));
        newTaskButton.setBackground(firstColor);
        newTaskButton.setForeground(secondColor);
        newTaskButton.setBorder(new LineBorder(secondColor, 3, true));
        newTaskButton.setFont(font);
        newTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPane.putPanelOnTop("NEW TASK");
            }
        });
        this.add(newTaskButton, new GridBagConstraints(
                0, 1, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 15, 0), 0, 10));
    }

    private void newMemberButtonInit() {
        newMemberButton.setText("Добавить участника");
        newMemberButton.setPreferredSize(new Dimension(200, 40));
        newMemberButton.setBackground(firstColor);
        newMemberButton.setForeground(secondColor);
        newMemberButton.setBorder(new LineBorder(secondColor, 3, true));
        newMemberButton.setFont(font);
        this.add(newMemberButton, new GridBagConstraints(
                0, 2, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 15, 0), 0, 10));
    }

    private void workspaceSettingsButtonInit() {
        workspaceSettingsButton.setText("<html>Настройки рабочего<p style=\"text-align:center;\">пространства</html>");
        workspaceSettingsButton.setPreferredSize(new Dimension(200, 70));
        workspaceSettingsButton.setBackground(firstColor);
        workspaceSettingsButton.setForeground(secondColor);
        workspaceSettingsButton.setBorder(new LineBorder(secondColor, 3, true));
        workspaceSettingsButton.setFont(font);
        this.add(workspaceSettingsButton, new GridBagConstraints(
                0, 3, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 15, 0), 0, 10));
    }
}
