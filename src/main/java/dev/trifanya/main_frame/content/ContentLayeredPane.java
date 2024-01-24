package dev.trifanya.main_frame.content;

import dev.trifanya.main_frame.MainFrame;
import dev.trifanya.main_frame.content.task_form.TaskFormPanel;
import dev.trifanya.main_frame.content.task_list.TaskListPanel;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

@Getter
public class ContentLayeredPane extends JLayeredPane {
    private TaskListPanel taskListPanel;
    private TaskFormPanel taskFormPanel;

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
        add(
                taskFormPanel,
                new GridBagConstraints(0, 0, 1, 1, 1, 1,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
        setLayer(taskFormPanel, new Integer(1));
    }

    public void putPanelOnTop(String contentPanel) {
        switch (contentPanel) {
            case "NEW TASK":
                setLayer(taskFormPanel, new Integer(1));
                setLayer(taskListPanel, new Integer(0));
                break;
            case "TASK LIST":
                setLayer(taskFormPanel, new Integer(0));
                setLayer(taskListPanel, new Integer(1));
                break;
        }
    }
}
