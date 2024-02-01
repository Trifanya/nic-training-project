package dev.trifanya.swing.content.task.task_list;

import dev.trifanya.swing.MainFrame;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class TaskListScrollPane extends JScrollPane {
    public TaskListScrollPane(TaskTable taskTable) {
        super(taskTable);
        viewport.setBackground(MainFrame.firstColor);
        setBorder(new LineBorder(MainFrame.secondColor, 3, true));
        setVerticalScrollBar(new JScrollBar());
        setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    }
}
