package dev.trifanya.swing_app.swing.content_panel.user.user_list;

import dev.trifanya.server_app.model.User;
import dev.trifanya.swing_app.swing.MainFrame;
import dev.trifanya.swing_app.swing.content_panel.ContentLayeredPane;
import lombok.Getter;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.jms.JMSException;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Getter
public class UserListPanel extends JPanel /*implements Runnable*/ {
    private ContentLayeredPane contentLayeredPane;

    private JLabel userListLabel;

    private UserTableModel userTableModel;
    private JTable userTable;
    private JScrollPane userListScrollPane;

    private JButton userDetailsButton;

    public UserListPanel() {
        setLayout(new GridBagLayout());
        setMinimumSize(new Dimension(600, 400));
        MainFrame.setBasicInterface(this);
    }

    public void init(ContentLayeredPane contentLayeredPane) throws JMSException, JsonProcessingException {
        this.contentLayeredPane = contentLayeredPane;

        initUserListLabel();
        add(userListLabel, new GridBagConstraints(
                0, 0, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(25, 25, 25, 25), 30, 20));

        userTableModel = new UserTableModel();
        initUserTable();
        initScrollPane();

        add(userListScrollPane, new GridBagConstraints(
                0, 1, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 25, 25, 25), 0, 0));

        initUserDetailsButton();
        add(userDetailsButton, new GridBagConstraints(
                0, 2, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 25, 25, 25), 50, 20));

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        /*(new Thread(this)).start();*/
    }

    private void initUserListLabel() {
        userListLabel = new JLabel("СПИСОК УЧАСТНИКОВ");
        userListLabel.setBackground(MainFrame.firstColor);
        userListLabel.setForeground(MainFrame.secondColor);
        userListLabel.setFont(MainFrame.font);
        userListLabel.setBorder(new LineBorder(MainFrame.secondColor, 3, true));
        userListLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void initUserTable() {
        userTable = new JTable(userTableModel);
        userTable.setBackground(MainFrame.firstColor);
        userTable.setForeground(MainFrame.secondColor);
        userTable.setFont(MainFrame.font);
        JTableHeader header = userTable.getTableHeader();
        header.setBackground(MainFrame.firstColor);
        header.setForeground(MainFrame.secondColor);
        header.setFont(MainFrame.font);
        userTable.setGridColor(MainFrame.secondColor);
        userTable.setSelectionBackground(MainFrame.secondColor);
        userTable.setSelectionForeground(MainFrame.firstColor);
        userTable.setFillsViewportHeight(true);

    }

    private void initScrollPane() {
        userListScrollPane = new JScrollPane(userTable);
        userListScrollPane.getViewport().setBackground(MainFrame.firstColor);
        userListScrollPane.setBorder(new LineBorder(MainFrame.secondColor, 3, true));
        userListScrollPane.setVerticalScrollBar(new JScrollBar());
        userListScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    }

    private void initUserDetailsButton() {
        userDetailsButton = new JButton("Подробнее");
        userDetailsButton.setBackground(MainFrame.firstColor);
        userDetailsButton.setForeground(MainFrame.secondColor);
        userDetailsButton.setFont(MainFrame.font);
        userDetailsButton.setBorder(new LineBorder(MainFrame.secondColor, 3, true));
        userDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = userTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(UserListPanel.this, "Вы не выбрали строку", "Предупреждение", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String email = userTableModel.getValueAt(selectedRow, 4);
                User user = userTableModel.getUserByEmail(email);
                contentLayeredPane.getUserDetailsPanel().fill(user);
                contentLayeredPane.setCurrentPanel(contentLayeredPane.getUserDetailsPanel());
            }
        });
    }

    /*@Override
    public void run() {
        while (true) {
            try {
                contentLayeredPane.getMainFrame().getUserMessageProducer().sendGetUserListMessage(null);
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (JMSException e) {
                throw new RuntimeException(e);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }*/
}
