package dev.trifanya.swing.content;

import dev.trifanya.swing.MainFrame;
import dev.trifanya.swing.content.task_form.TaskFormPanel;
import dev.trifanya.swing.content.task_list.TaskListPanel;
import dev.trifanya.swing.content.task_page.TaskDetailsPanel;
import lombok.Getter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

@Getter
public class ContentLayeredPane extends JLayeredPane {
    private TaskListPanel taskListPanel;
    private TaskFormPanel taskFormPanel;
    private TaskDetailsPanel taskDetailsPanel;

    public ContentLayeredPane() {
        setLayout(new GridBagLayout());
        setBackground(MainFrame.firstColor);
        setBorder(new LineBorder(MainFrame.secondColor, 3, true));
        setMinimumSize(new Dimension(600, 400));
    }

    public void init() {
        taskListPanel = new TaskListPanel();
        taskListPanel.init(this);
        add(taskListPanel, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        setLayer(taskListPanel, new Integer(0));

        taskFormPanel = new TaskFormPanel();
        taskFormPanel.init(this);
        add(taskFormPanel, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
        setLayer(taskFormPanel, new Integer(1));

        taskDetailsPanel = new TaskDetailsPanel();
        taskDetailsPanel.init();
        add(taskDetailsPanel, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
        setLayer(taskDetailsPanel, new Integer(0));
    }

    public void putPanelOnTop(String contentPanel) {
        switch (contentPanel) {
            case "NEW TASK":
                setLayer(taskFormPanel, new Integer(1));
                setLayer(taskListPanel, new Integer(0));
                setLayer(taskDetailsPanel, new Integer(0));
                break;
            case "TASK LIST":
                setLayer(taskFormPanel, new Integer(0));
                setLayer(taskListPanel, new Integer(1));
                setLayer(taskDetailsPanel, new Integer(0));
                break;
            case "TASK DETAILS":
                setLayer(taskFormPanel, new Integer(0));
                setLayer(taskListPanel, new Integer(0));
                setLayer(taskDetailsPanel, new Integer(1));
                break;
        }
    }
}
