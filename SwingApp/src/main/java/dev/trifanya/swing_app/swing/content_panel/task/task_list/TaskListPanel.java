package dev.trifanya.swing_app.swing.content_panel.task.task_list;

import dev.trifanya.server_app.model.Task;
import dev.trifanya.swing_app.swing.MainFrame;
import dev.trifanya.swing_app.swing.content_panel.ContentLayeredPane;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TaskListPanel extends JPanel /*implements Runnable*/ {
    private ContentLayeredPane contentLayeredPane;

    private JLabel taskListLabel;

    private TaskTableModel taskTableModel;
    private JTable taskTable;
    private JScrollPane taskListScrollPane;

    private JButton taskDetailsButton;
    private JButton createTaskButton;
    private JButton updateTaskButton;
    private JButton deleteTaskButton;

    private List<JButton> panelButtons = new ArrayList<>();

    public TaskListPanel() {
        setLayout(new GridBagLayout());
        setMinimumSize(new Dimension(600, 400));
        MainFrame.setBasicInterface(this);
    }

    public void init(ContentLayeredPane contentLayeredPane) {
        this.contentLayeredPane = contentLayeredPane;

        initTaskListLabel();
        add(taskListLabel, new GridBagConstraints(
                1, 0, 2, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(25, 25, 25, 25), 30, 20));

        taskTableModel = new TaskTableModel();
        initTaskTable();
        initScrollPane();

        add(taskListScrollPane, new GridBagConstraints(
                0, 1, 4, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 25, 25, 25), 0, 0));

        initTaskDetailsButton();
        initCreateTaskButton();
        initUpdateTaskButton();
        initDeleteTaskButton();

        int gridx = 0;
        for (JButton button : panelButtons) {
            button.setBackground(MainFrame.firstColor);
            button.setForeground(MainFrame.secondColor);
            button.setFont(MainFrame.font);
            button.setBorder(new LineBorder(MainFrame.secondColor, 3, true));
            add(button, new GridBagConstraints(
                    gridx++, 2, 1, 1, 1, 0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 25, 25, 25), 0, 10));
        }

        /*(new Thread(this)).start();*/
    }

    private void initTaskListLabel() {
        taskListLabel = new JLabel("СПИСОК ЗАДАЧ");
        taskListLabel.setBackground(MainFrame.firstColor);
        taskListLabel.setForeground(MainFrame.secondColor);
        taskListLabel.setFont(MainFrame.font);
        taskListLabel.setBorder(new LineBorder(MainFrame.secondColor, 3, true));
        taskListLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void initTaskTable() {
        taskTable = new JTable(taskTableModel);
        taskTable.setBackground(MainFrame.firstColor);
        taskTable.setForeground(MainFrame.secondColor);
        taskTable.setFont(MainFrame.font);
        JTableHeader header = taskTable.getTableHeader();
        header.setBackground(MainFrame.firstColor);
        header.setForeground(MainFrame.secondColor);
        header.setFont(MainFrame.font);
        taskTable.setGridColor(MainFrame.secondColor);
        taskTable.setSelectionBackground(MainFrame.secondColor);
        taskTable.setSelectionForeground(MainFrame.firstColor);
        taskTable.setFillsViewportHeight(true);
    }

    private void initScrollPane() {
        taskListScrollPane = new JScrollPane(taskTable);
        taskListScrollPane.getViewport().setBackground(MainFrame.firstColor);
        taskListScrollPane.setBorder(new LineBorder(MainFrame.secondColor, 3, true));
        taskListScrollPane.setVerticalScrollBar(new JScrollBar());
        taskListScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    }

    private void initTaskDetailsButton() {
        taskDetailsButton = new JButton("Подробнее");
        taskDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = taskTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(TaskListPanel.this, "Вы не выбрали строку", "Предупреждение", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int taskId = Integer.valueOf(taskTableModel.getValueAt(selectedRow, 0));
                Task currentTask = contentLayeredPane.getTaskListPanel().getTaskTableModel().getTaskById(taskId);
                contentLayeredPane.getTaskDetailsPanel().setCurrentTask(currentTask);
                contentLayeredPane.getTaskDetailsPanel().fill();
                contentLayeredPane.setCurrentPanel(contentLayeredPane.getTaskDetailsPanel());
            }
        });
        panelButtons.add(taskDetailsButton);
    }

    private void initCreateTaskButton() {
        createTaskButton = new JButton("Создать");
        createTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentLayeredPane.getTaskFormPanel().setCurrentTask(null);
                contentLayeredPane.setCurrentPanel(contentLayeredPane.getTaskFormPanel());
            }
        });
        panelButtons.add(createTaskButton);
    }

    private void initUpdateTaskButton() {
        updateTaskButton = new JButton("Редактировать");
        updateTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = taskTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(TaskListPanel.this, "Вы не выбрали строку", "Предупреждение", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int taskId = Integer.valueOf(taskTableModel.getValueAt(selectedRow, 0));
                Task currentTask = contentLayeredPane.getTaskListPanel().getTaskTableModel().getTaskById(taskId);
                contentLayeredPane.getTaskFormPanel().setCurrentTask(currentTask);
                contentLayeredPane.setCurrentPanel(contentLayeredPane.getTaskFormPanel());
            }
        });
        panelButtons.add(updateTaskButton);
    }

    private void initDeleteTaskButton() {
        deleteTaskButton = new JButton("Удалить");
        deleteTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = taskTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(TaskListPanel.this, "Вы не выбрали строку", "Предупреждение", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int taskId = Integer.valueOf(taskTableModel.getValueAt(selectedRow, 0));
                contentLayeredPane.getMainFrame().getTaskMessageProducer().sendDeleteTaskMessage(taskId);
            }
        });
        panelButtons.add(deleteTaskButton);
    }

    /** Метод для регулярного обновления списка задач */
    /*@Override
    public void run() {
        while (true) {
            try {
                Map<String, String> filters = contentLayeredPane.getMainFrame().getSortAndFiltersPanel().getFilters();
                contentLayeredPane.getMainFrame().getTaskMessageProducer().sendGetTaskListMessage(filters);
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
