package dev.trifanya.component;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ContentLayeredPane extends JLayeredPane {
    private Color firstColor = Color.BLACK;
    private Color secondColor = Color.ORANGE;
    private Font font = new Font(Font.SANS_SERIF, Font.BOLD, 16);

    private TaskListPanel taskListPanel = new TaskListPanel();

    private TaskFormPanel taskFormPanel = new TaskFormPanel();

    public ContentLayeredPane() {
        this.setLayout(new GridBagLayout());
        this.setBackground(firstColor);
        this.setBorder(new LineBorder(secondColor, 3, true));
        this.setMinimumSize(new Dimension(600, 400));
    }

    public void init() {
        taskListPanel.init();
        this.add(taskListPanel, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        this.setLayer(taskListPanel, new Integer(0));

        taskFormPanel.init();
        this.add(
                taskFormPanel,
                new GridBagConstraints(0, 0, 1, 1, 1, 1,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
        this.setLayer(taskFormPanel, new Integer(1));
    }

    public void putPanelOnTop(String contentPanel) {
        switch (contentPanel) {
            case "NEW TASK":
                this.setLayer(taskFormPanel, new Integer(1));
                this.setLayer(taskListPanel, new Integer(0));
                break;
            case "TASK LIST":
                this.setLayer(taskFormPanel, new Integer(0));
                this.setLayer(taskListPanel, new Integer(1));
                break;
        }
    }
}
