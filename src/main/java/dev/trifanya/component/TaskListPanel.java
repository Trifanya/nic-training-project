package dev.trifanya.component;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class TaskListPanel extends JPanel implements Runnable {
    private Color firstColor = Color.BLACK;
    private Color secondColor = Color.ORANGE;
    private Font font = new Font(Font.SANS_SERIF, Font.BOLD, 16);

    private JLabel taskListLabel = new JLabel();

    private TaskTableModel taskTableModel;
    private JTable taskTable;
    private JScrollPane taskListScrollPane;

    public TaskListPanel() {
        this.setLayout(new GridBagLayout());
        this.setBackground(firstColor);
        this.setBorder(new LineBorder(secondColor, 3, true));
        this.setMinimumSize(new Dimension(600, 400));
    }

    public void init() {
        taskListLabel.setText("СПИСОК ЗАДАЧ");
        taskListLabel.setBackground(firstColor);
        taskListLabel.setForeground(secondColor);
        taskListLabel.setFont(font);
        taskListLabel.setBorder(new LineBorder(secondColor, 3, true));
        taskListLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(taskListLabel, new GridBagConstraints(
                0, 0, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(25, 25, 25, 25), 30, 20));

        taskTableModel = new TaskTableModel();
        taskTableModel.fillTable();
        taskTable = new JTable(taskTableModel);
        taskListScrollPane = new JScrollPane(taskTable);

        taskListScrollPane.setBorder(new LineBorder(secondColor, 3, true));;
        this.add(taskListScrollPane, new GridBagConstraints(
                0, 1, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 25, 25, 25), 0, 0));

        (new Thread(this)).start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                taskTableModel.fillTable();
                this.repaint();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
