package dev.trifanya.swing_app.swing.content_panel.task.task_form;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.lgooddatepicker.components.DateTimePicker;

import dev.trifanya.server_app.model.Task;
import dev.trifanya.server_app.model.TaskPriority;
import dev.trifanya.server_app.model.TaskStatus;
import dev.trifanya.server_app.model.User;
import dev.trifanya.server_app.validator.TaskValidator;
import dev.trifanya.swing_app.swing.content_panel.ContentLayeredPane;

import lombok.Getter;
import lombok.Setter;

import dev.trifanya.swing_app.swing.MainFrame;

import java.awt.*;
import javax.jms.JMSException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;

@Getter
@Setter
public class TaskFormPanel extends JPanel {
    private Task currentTask;
    private TaskValidator taskValidator;

    private ContentLayeredPane contentLayeredPane;

    private final int leftMargin = 25;
    private final int rightMargin = 25;
    private final int topMargin = 25;
    private final int bottomMargin = 25;

    private int currentRow = 0;
    private int currentColumn = 0;

    private int ipadx = 10;
    private int ipady = 10;

    private final int labelHorizontalAlignment = SwingConstants.LEFT;

    private JLabel pageTitleLabel;
    private JPanel taskFormPanel;

    private JLabel titleLabel;
    private JTextField titleField;

    private JLabel performerLabel;
    private JComboBox performerBox;

    private JLabel priorityLabel;
    private JComboBox priorityBox;

    private JLabel deadlineDateLabel;
    private DateTimePicker dateTimePicker;

    private JLabel descriptionLabel;
    private JTextArea descriptionArea;

    private JButton submitButton;

    public TaskFormPanel() {
        taskValidator = new TaskValidator();
        setLayout(new GridBagLayout());
        setMinimumSize(new Dimension(600, 400));
        setBackground(MainFrame.firstColor);
        setBorder(new LineBorder(MainFrame.secondColor, 3, true));
    }

    public void init(ContentLayeredPane contentLayeredPane) {
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
        titleLabel.setBorder(null);
        titleLabel.setHorizontalAlignment(labelHorizontalAlignment);
        taskFormPanel.add(titleLabel, new GridBagConstraints(currentColumn++, currentRow, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(topMargin, leftMargin, bottomMargin, 0), ipadx, ipady));

        titleField = new JTextField();
        titleField.setColumns(40);
        MainFrame.setBasicInterface(titleField);
        titleField.setCaretColor(MainFrame.secondColor);
        titleField.setHorizontalAlignment(SwingConstants.CENTER);
        taskFormPanel.add(titleField, new GridBagConstraints(currentColumn--, currentRow++, 1, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(topMargin, leftMargin, bottomMargin, rightMargin), (int) (ipadx * 1.8), (int) (ipady * 1.8)));
    }

    private void initPerformerRow() {
        performerLabel = new JLabel("Исполнитель:");
        MainFrame.setBasicInterface(performerLabel);
        performerLabel.setBorder(null);
        performerLabel.setHorizontalAlignment(labelHorizontalAlignment);
        taskFormPanel.add(performerLabel, new GridBagConstraints(currentColumn++, currentRow, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, leftMargin, bottomMargin, 0), ipadx, ipady));

        performerBox = new JComboBox();
        MainFrame.setBasicInterface(performerBox);
        ((JLabel) performerBox.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        for (User user : contentLayeredPane.getUserListPanel().getUserTableModel().getUserList()) {
            performerBox.addItem(user.getEmail() + " (" + user.getName() + " " + user.getSurname() + ")");
        }
        taskFormPanel.add(performerBox, new GridBagConstraints(currentColumn--, currentRow++, 1, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, leftMargin, bottomMargin, rightMargin), ipadx, ipady));
    }

    private void initPriorityRow() {
        priorityLabel = new JLabel("Приоритет:");
        MainFrame.setBasicInterface(priorityLabel);
        priorityLabel.setBorder(null);
        priorityLabel.setHorizontalAlignment(labelHorizontalAlignment);
        taskFormPanel.add(priorityLabel, new GridBagConstraints(currentColumn++, currentRow, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, leftMargin, bottomMargin, 0), ipadx, ipady));

        priorityBox = new JComboBox();
        MainFrame.setBasicInterface(priorityBox);
        ((JLabel) priorityBox.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        for (TaskPriority priority : TaskPriority.values()) {
            priorityBox.addItem(priority.getRuString());
        }
        taskFormPanel.add(priorityBox, new GridBagConstraints(currentColumn--, currentRow++, 1, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, leftMargin, bottomMargin, rightMargin), ipadx, ipady));
    }

    private void initDeadlineRow() {
        deadlineDateLabel = new JLabel("Дедлайн:");
        MainFrame.setBasicInterface(deadlineDateLabel);
        deadlineDateLabel.setBorder(null);
        deadlineDateLabel.setHorizontalAlignment(labelHorizontalAlignment);
        taskFormPanel.add(deadlineDateLabel, new GridBagConstraints(
                currentColumn++, currentRow, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, leftMargin, bottomMargin, 0), ipadx, ipady));

        dateTimePicker = new DateTimePicker();

        dateTimePicker.getDatePicker().setBorder(MainFrame.border);
        dateTimePicker.getDatePicker().getSettings().setFontValidDate(MainFrame.font);
        dateTimePicker.getDatePicker().getSettings().setFontInvalidDate(MainFrame.font);
        dateTimePicker.getDatePicker().getComponentDateTextField().setHorizontalAlignment(SwingConstants.CENTER);

        dateTimePicker.getTimePicker().setBorder(MainFrame.border);
        dateTimePicker.getTimePicker().getSettings().fontValidTime = MainFrame.font;
        dateTimePicker.getTimePicker().getSettings().fontInvalidTime = MainFrame.font;
        dateTimePicker.getTimePicker().getComponentTimeTextField().setHorizontalAlignment(SwingConstants.CENTER);

        taskFormPanel.add(dateTimePicker, new GridBagConstraints(
                currentColumn--, currentRow++, 1, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, leftMargin, bottomMargin, rightMargin), ipadx, 13));
    }

    private void initDescriptionRow() {
        descriptionLabel = new JLabel("Описание:");
        MainFrame.setBasicInterface(descriptionLabel);
        descriptionLabel.setBorder(null);
        descriptionLabel.setHorizontalAlignment(labelHorizontalAlignment);
        descriptionLabel.setVerticalAlignment(SwingConstants.TOP);
        taskFormPanel.add(descriptionLabel, new GridBagConstraints(currentColumn++, currentRow, 1, 1, 0, 0,
                GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
                new Insets(0, leftMargin, bottomMargin, 0), ipadx, ipady));

        descriptionArea = new JTextArea();
        descriptionArea.setColumns(40);
        descriptionArea.setRows(8);
        MainFrame.setBasicInterface(descriptionArea);
        taskFormPanel.add(descriptionArea, new GridBagConstraints(currentColumn--, currentRow++, 1, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, leftMargin, bottomMargin, rightMargin), ipadx, ipady));
    }

    public void initSubmitButton() {
        submitButton = new JButton("СОЗДАТЬ ЗАДАЧУ");
        submitButton.setPreferredSize(new Dimension(200, 40));
        MainFrame.setBasicInterface(submitButton);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User author = contentLayeredPane.getUserDetailsPanel().getCurrentUser();
                String performerEmail = performerBox.getSelectedItem().toString().split(" ")[0];
                Task taskToSave = new Task()
                        .setTitle(titleField.getText())
                        .setDescription(descriptionArea.getText())
                        .setAuthor(author)
                        .setPerformer(contentLayeredPane.getUserListPanel().getUserTableModel().getUserByEmail(performerEmail))
                        .setStatus(TaskStatus.NOT_STARTED)
                        .setPriority(TaskPriority.getTaskPriority(priorityBox.getSelectedItem().toString()))
                        .setDeadline(dateTimePicker.getDateTimePermissive());
                try {
                    taskValidator.validateTask(taskToSave);
                } catch (Exception exception) {
                    System.out.println(taskToSave.getAuthor());
                    System.out.println(taskToSave.getPerformer());
                    System.out.println(exception.getMessage());
                    JOptionPane.showMessageDialog(
                            TaskFormPanel.this, exception.getMessage(),
                            "Предупреждение", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (currentTask == null) {
                    try {
                        contentLayeredPane.getMainFrame().getTaskMessageProducer().sendCreateTaskMessage(taskToSave);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    clearTaskForm();
                } else {
                    taskToSave.setId(currentTask.getId());
                    try {
                        contentLayeredPane.getMainFrame().getTaskMessageProducer().sendUpdateTaskMessage(taskToSave);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
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
