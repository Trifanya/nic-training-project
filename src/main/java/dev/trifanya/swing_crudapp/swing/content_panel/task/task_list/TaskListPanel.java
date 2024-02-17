package dev.trifanya.swing_crudapp.swing.content_panel.task.task_list;

import dev.trifanya.swing_crudapp.service.TaskService;
import dev.trifanya.swing_crudapp.swing.MainFrame;
import dev.trifanya.swing_crudapp.swing.content_panel.ContentLayeredPane;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class TaskListPanel extends JPanel implements Runnable {
    private TaskService taskService;
    private ContentLayeredPane contentLayeredPane;

    private JLabel taskListLabel;

    private TaskTableModel taskTableModel;
    private TaskTable taskTable;
    private TaskListScrollPane taskListScrollPane;

    private JButton taskDetailsButton;
    private JButton createTaskButton;
    private JButton updateTaskButton;
    private JButton deleteTaskButton;

    private Map<String, String> taskFilters;
    private String sortByColumn = "id";
    private String sortDir = "ASC";

    public TaskListPanel() {
        setLayout(new GridBagLayout());
        setMinimumSize(new Dimension(600, 400));
        MainFrame.setBasicInterface(this);
    }

    public void init(ContentLayeredPane contentLayeredPane) {
        taskService = new TaskService();

        this.contentLayeredPane = contentLayeredPane;

        initTaskListLabel();
        add(taskListLabel, new GridBagConstraints(
                1, 0, 2, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(25, 25, 25, 25), 30, 20));

        taskTableModel = new TaskTableModel();
        taskTable = new TaskTable(taskTableModel);
        taskListScrollPane = new TaskListScrollPane(taskTable);

        add(taskListScrollPane, new GridBagConstraints(
                0, 1, 4, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 25, 25, 25), 0, 0));

        initTaskDetailsButton();
        add(taskDetailsButton, new GridBagConstraints(
                0, 2, 1, 1, 1, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 25, 25, 25), 0, 0));

        initCreateTaskButton();
        add(createTaskButton, new GridBagConstraints(
                1, 2, 1, 1, 1, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 25, 25, 25), 0, 0));

        initUpdateTaskButton();
        add(updateTaskButton, new GridBagConstraints(
                2, 2, 1, 1, 1, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 25, 25, 25), 0, 0));

        initDeleteTaskButton();
        add(deleteTaskButton, new GridBagConstraints(
                3, 2, 1, 1, 1, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 25, 25, 25), 0, 0));

        taskFilters = new HashMap<>();

        (new Thread(this)).start();
    }

    public void initTaskListLabel() {
        taskListLabel = new JLabel("СПИСОК ЗАДАЧ");
        taskListLabel.setBackground(MainFrame.firstColor);
        taskListLabel.setForeground(MainFrame.secondColor);
        taskListLabel.setFont(MainFrame.font);
        taskListLabel.setBorder(new LineBorder(MainFrame.secondColor, 3, true));
        taskListLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public void initTaskDetailsButton() {
        taskDetailsButton = new JButton("Подробнее");
        taskDetailsButton.setBackground(MainFrame.firstColor);
        taskDetailsButton.setForeground(MainFrame.secondColor);
        taskDetailsButton.setFont(MainFrame.font);
        taskDetailsButton.setBorder(new LineBorder(MainFrame.secondColor, 3, true));
        taskDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = taskTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(TaskListPanel.this, "Вы не выбрали строку", "Предупреждение", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int taskId = Integer.valueOf(taskTableModel.getValueAt(selectedRow, 0));
                contentLayeredPane.getTaskDetailsPanel().setCurrentTask(taskService.getTaskById(taskId));
                contentLayeredPane.getTaskDetailsPanel().fill();
                contentLayeredPane.putPanelOnTop("TASK DETAILS");
            }
        });
    }

    public void initCreateTaskButton() {
        createTaskButton = new JButton("Создать");
        createTaskButton.setBackground(MainFrame.firstColor);
        createTaskButton.setForeground(MainFrame.secondColor);
        createTaskButton.setFont(MainFrame.font);
        createTaskButton.setBorder(new LineBorder(MainFrame.secondColor, 3, true));
        createTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentLayeredPane.getTaskFormPanel().setCurrentTask(null);
                contentLayeredPane.putPanelOnTop("NEW TASK");
            }
        });
    }

    public void initUpdateTaskButton() {
        updateTaskButton = new JButton("Редактировать");
        updateTaskButton.setBackground(MainFrame.firstColor);
        updateTaskButton.setForeground(MainFrame.secondColor);
        updateTaskButton.setFont(MainFrame.font);
        updateTaskButton.setBorder(new LineBorder(MainFrame.secondColor, 3, true));
        updateTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = taskTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(TaskListPanel.this, "Вы не выбрали строку", "Предупреждение", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                contentLayeredPane.putPanelOnTop("NEW TASK");
                int taskId = Integer.valueOf(taskTableModel.getValueAt(selectedRow, 0));
                contentLayeredPane.getTaskFormPanel().setCurrentTask(taskService.getTaskById(taskId));
            }
        });
    }

    public void initDeleteTaskButton() {
        deleteTaskButton = new JButton("Удалить");
        deleteTaskButton.setBackground(MainFrame.firstColor);
        deleteTaskButton.setForeground(MainFrame.secondColor);
        deleteTaskButton.setFont(MainFrame.font);
        deleteTaskButton.setBorder(new LineBorder(MainFrame.secondColor, 3, true));
        deleteTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = taskTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(TaskListPanel.this, "Вы не выбрали строку", "Предупреждение", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int taskId = Integer.valueOf(taskTableModel.getValueAt(selectedRow, 0));
                System.out.println(taskId);
                try {
                    taskService.deleteTaskById(taskId);
                    JOptionPane.showMessageDialog(TaskListPanel.this, "Задача успешно удалена", "Сообщение", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(TaskListPanel.this, "Что-то пошло не так. Невозможно удалить задачу", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    @Override
    public void run() {
        while (true) {
            try {
                taskTableModel.fillTable(taskFilters, sortByColumn, sortDir);
                this.repaint();
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
