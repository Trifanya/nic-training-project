package dev.trifanya.swing.content.user.user_list;

import dev.trifanya.swing.MainFrame;
import dev.trifanya.swing.content.task.task_list.TaskTable;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class UserListScrollPane extends JScrollPane {
    public UserListScrollPane(UserTable userTable) {
        super(userTable);
        viewport.setBackground(MainFrame.firstColor);
        setBorder(new LineBorder(MainFrame.secondColor, 3, true));
        setVerticalScrollBar(new JScrollBar());
        setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    }
}
