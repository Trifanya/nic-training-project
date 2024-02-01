package dev.trifanya.swing.content.task.task_form;

import dev.trifanya.service.TaskService;
import dev.trifanya.service.UserService;
import dev.trifanya.swing.content.ContentLayeredPane;
import lombok.Getter;
import lombok.Setter;
import dev.trifanya.model.Task;
import dev.trifanya.model.User;
import dev.trifanya.model.TaskStatus;
import dev.trifanya.model.TaskPriority;
import dev.trifanya.swing.MainFrame;
import org.jdatepicker.impl.UtilDateModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;

import java.awt.*;
import javax.swing.*;
import java.util.Properties;
import java.time.LocalDateTime;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import javax.swing.text.DateFormatter;

@Getter
@Setter
public class TaskFormPanel extends JPanel {
    private UserService userService;
    private TaskService taskService;

    private Task currentTask;

    private ContentLayeredPane contentLayeredPane;

    private final int leftMargin = 25;
    private final int rightMargin = 25;
    private final int topMargin = 25;
    private final int bottomMargin = 25;

    private int currentRow = 0;
    private int currentColumn = 0;

    private final int labelHorizontalAlignment = SwingConstants.LEFT;

    private JLabel pageTitleLabel;
    private JPanel taskFormPanel;

    private JLabel titleLabel;
    private JTextField titleField;

    private JLabel performerLabel;
    private JComboBox performerBox;

    private JLabel priorityLabel;
    private JComboBox priorityBox;

    private JLabel deadlineLabel;
    private JDatePickerImpl datePicker;

    private JLabel descriptionLabel;
    private JTextArea descriptionArea;

    private JButton submitButton;

    public TaskFormPanel() {
        setLayout(new GridBagLayout());
        setMinimumSize(new Dimension(600, 400));
        setBackground(MainFrame.firstColor);
        setBorder(new LineBorder(MainFrame.secondColor, 3, true));
    }

    public void init(ContentLayeredPane contentLayeredPane) {
        taskService = new TaskService();
        userService = new UserService();

        this.contentLayeredPane = contentLayeredPane;

        initPageTitleLabel();
        initTaskFormPanel();
        initTaskTitleRow();
        initPerformerRow();
        initPriorityRow();
        initDeadlineRow();
        initDescriptionRow();
        initSubmitButton();
    }

    public void initPageTitleLabel() {
        pageTitleLabel = new JLabel("СОЗДАНИЕ НОВОЙ ЗАДАЧИ");
        MainFrame.setBasicInterface(pageTitleLabel);
        pageTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(pageTitleLabel, new GridBagConstraints(
                0, 0, 1, 1, 0, 0,
                GridBagConstraints.NORTH, GridBagConstraints.NONE,
                new Insets(topMargin, leftMargin, bottomMargin, rightMargin), 30, 20));
    }

    public void initTaskFormPanel() {
        taskFormPanel = new JPanel();
        taskFormPanel.setLayout(new GridBagLayout());
        MainFrame.setBasicInterface(taskFormPanel);
        add(taskFormPanel, new GridBagConstraints(
                0, 1, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 25, 25, 25), 0, 0));
    }

    private void initTaskTitleRow() {
        titleLabel = new JLabel("Заголовок:");
        MainFrame.setBasicInterface(titleLabel);
        titleLabel.setHorizontalAlignment(labelHorizontalAlignment);
        taskFormPanel.add(titleLabel, new GridBagConstraints(currentColumn++, currentRow, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(topMargin, leftMargin, bottomMargin, rightMargin), 10, 10));

        titleField = new JTextField();
        titleField.setColumns(40);
        MainFrame.setBasicInterface(titleField);
        titleField.setCaretColor(MainFrame.secondColor);
        titleField.setHorizontalAlignment(SwingConstants.LEFT);
        taskFormPanel.add(titleField, new GridBagConstraints(currentColumn--, currentRow++, 2, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(topMargin, leftMargin, bottomMargin, rightMargin), 10, 10));
    }

    private void initPerformerRow() {
        performerLabel = new JLabel("Исполнитель:");
        MainFrame.setBasicInterface(performerLabel);
        performerLabel.setHorizontalAlignment(labelHorizontalAlignment);
        taskFormPanel.add(performerLabel, new GridBagConstraints(currentColumn++, currentRow, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 10, 10));

        performerBox = new JComboBox();
        MainFrame.setBasicInterface(performerBox);
        for (User user : userService.getUsers("id", "ASC")) {
            performerBox.addItem(user.getEmail() + " (" + user.getName() + " " + user.getSurname() + ")");
        }
        taskFormPanel.add(performerBox, new GridBagConstraints(currentColumn--, currentRow++, 2, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 10, 10));
    }

    private void initPriorityRow() {
        priorityLabel = new JLabel("Приоритет:");
        MainFrame.setBasicInterface(priorityLabel);
        priorityLabel.setHorizontalAlignment(labelHorizontalAlignment);
        taskFormPanel.add(priorityLabel, new GridBagConstraints(currentColumn++, currentRow, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 10, 10));

        priorityBox = new JComboBox();
        MainFrame.setBasicInterface(priorityBox);
        for (TaskPriority priority : TaskPriority.values()) {
            priorityBox.addItem(priority.getRuString());
        }
        taskFormPanel.add(priorityBox, new GridBagConstraints(currentColumn--, currentRow++, 1, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 10, 10));
    }

    private void initDeadlineRow() {
        deadlineLabel = new JLabel("Дедлайн:");
        MainFrame.setBasicInterface(deadlineLabel);
        deadlineLabel.setHorizontalAlignment(labelHorizontalAlignment);
        taskFormPanel.add(deadlineLabel, new GridBagConstraints(currentColumn++, currentRow, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 10, 10));

        UtilDateModel dateModel = new UtilDateModel();
        dateModel.setDate(2024, 0, 1);
        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, new Properties());
        datePicker = new JDatePickerImpl(datePanel, new DateFormatter());
        MainFrame.setBasicInterface(datePicker);
        taskFormPanel.add(datePicker, new GridBagConstraints(currentColumn--, currentRow++, 2, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 10, 10));
    }

    private void initDescriptionRow() {
        descriptionLabel = new JLabel("Описание:");
        MainFrame.setBasicInterface(descriptionLabel);
        descriptionLabel.setHorizontalAlignment(labelHorizontalAlignment);
        descriptionLabel.setVerticalAlignment(SwingConstants.TOP);
        taskFormPanel.add(descriptionLabel, new GridBagConstraints(currentColumn++, currentRow, 1, 1, 0, 0,
                GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 10, 10));

        descriptionArea = new JTextArea();
        descriptionArea.setColumns(40);
        descriptionArea.setRows(8);
        MainFrame.setBasicInterface(descriptionArea);
        taskFormPanel.add(descriptionArea, new GridBagConstraints(currentColumn--, currentRow++, 2, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 10, 10));
    }

    public void initSubmitButton() {
        submitButton = new JButton("СОЗДАТЬ ЗАДАЧУ");
        submitButton.setPreferredSize(new Dimension(200, 40));
        MainFrame.setBasicInterface(submitButton);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDateTime testDeadline = LocalDateTime.now().plusDays(5);
                String performerEmail = performerBox.getSelectedItem().toString().split(" ")[0];

                Task taskToSave = new Task()
                        .setTitle(titleField.getText())
                        .setDescription(descriptionArea.getText())
                        .setAuthor(userService.getUserById(1))
                        .setPerformer(userService.getUserByEmail(performerEmail))
                        .setStatus(TaskStatus.NOT_STARTED)
                        .setPriority(TaskPriority.getTaskPriority(priorityBox.getSelectedItem().toString()))
                        .setDeadline(testDeadline);

                if (currentTask == null) {
                    taskService.createNewTask(taskToSave);
                } else {
                    taskToSave.setId(currentTask.getId());
                    taskService.updateTaskInfo(taskToSave);
                }
            }
        });
        add(submitButton, new GridBagConstraints(
                0, 5, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 50, 20));
    }

    /**
     * Заполнение формы данными редактируемой задачи
     */
    public void fillTaskForm(Task task) {
        titleField.setText(task.getTitle());
        User user = task.getPerformer();
        performerBox.setSelectedItem(user.getEmail() + " (" + user.getName() + " " + user.getSurname() + ")");
        priorityBox.setSelectedItem(task.getPriority().getRuString());
        descriptionArea.setText(task.getDescription());
    }

    /**
     * Очистка формы от данных
     */
    public void clearTaskForm() {
        titleField.setText("");
        performerBox.setSelectedItem(null);
        priorityBox.setSelectedItem(null);
        descriptionArea.setText("");
    }

    public void rewriteToUpdate() {
        pageTitleLabel.setText("ИЗМЕНЕНИЕ ЗАДАЧИ");
        submitButton.setText("СОХРАНИТЬ ИЗМЕНЕНИЯ");
    }

    public void rewriteToCreate() {
        pageTitleLabel.setText("СОЗДАНИЕ ЗАДАЧИ");
        submitButton.setText("СОЗДАТЬ ЗАДАЧУ");
    }

    public void setCurrentTask(Task task) {
        this.currentTask = task;
        if (task == null) {
            clearTaskForm();
            rewriteToCreate();
            return;
        }
        fillTaskForm(task);
        rewriteToUpdate();
    }
}
