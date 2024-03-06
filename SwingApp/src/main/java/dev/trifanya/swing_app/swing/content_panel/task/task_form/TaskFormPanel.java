package dev.trifanya.swing_app.swing.content_panel.task.task_form;

import com.github.lgooddatepicker.components.DateTimePicker;

import dev.trifanya.server_app.model.Task;
import dev.trifanya.server_app.model.TaskPriority;
import dev.trifanya.server_app.model.TaskStatus;
import dev.trifanya.server_app.model.User;
import dev.trifanya.swing_app.swing.content_panel.ContentLayeredPane;

import lombok.Getter;
import lombok.Setter;

import dev.trifanya.swing_app.swing.MainFrame;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class TaskFormPanel extends JPanel {
    private Task currentTask;

    private ContentLayeredPane contentLayeredPane;

    private JLabel pageTitleLabel;
    private JPanel taskFormPanel;
    private JButton submitButton;

    private JLabel titleLabel;
    private JLabel performerLabel;
    private JLabel priorityLabel;
    private JLabel deadlineDateLabel;
    private JLabel descriptionLabel;

    private JTextField titleField;
    private JComboBox performerBox;
    private JComboBox priorityBox;
    private DateTimePicker dateTimePicker;
    private JTextArea descriptionArea;

    private Map<JLabel, JComponent> formLines = new LinkedHashMap<>();

    private final int leftMargin = 25;
    private final int rightMargin = 25;
    private final int topMargin = 25;
    private final int bottomMargin = 25;

    private int currentRow = 0;
    private int currentColumn = 0;

    private int ipadx = 0;
    private int ipady = 0;

    private final int labelHorizontalAlignment = SwingConstants.LEFT;

    public TaskFormPanel() {
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

        for (Map.Entry<JLabel, JComponent> formLine : formLines.entrySet()) {
            MainFrame.setBasicInterface(formLine.getKey());
            formLine.getKey().setHorizontalAlignment(labelHorizontalAlignment);
            formLine.getKey().setBorder(null);
            taskFormPanel.add(formLine.getKey(), new GridBagConstraints(
                    currentColumn++, currentRow, 1, 1, 0, 0,
                    GridBagConstraints.EAST, GridBagConstraints.NONE,
                    new Insets(topMargin, leftMargin, bottomMargin, 0), ipadx, ipady));
            taskFormPanel.add(formLine.getValue(), new GridBagConstraints(
                    currentColumn--, currentRow++, 1, 1, 0, 0,
                    GridBagConstraints.WEST, GridBagConstraints.BOTH,
                    new Insets(10, leftMargin, 10, rightMargin), ipadx, ipady));
        }
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
                new Insets(0, 25, 0, 25), 0, 0));
    }

    public void initSubmitButton() {
        submitButton = new JButton("СОЗДАТЬ ЗАДАЧУ");
        submitButton.setPreferredSize(new Dimension(200, 40));
        MainFrame.setBasicInterface(submitButton);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String validationResult = validateForm();
                if (!validationResult.equals("+")) {
                    contentLayeredPane.getMainFrame().showWarning(validationResult);
                    return;
                }
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
                if (currentTask == null) {
                    contentLayeredPane.getMainFrame().getTaskMessageProducer().sendCreateTaskMessage(taskToSave);
                } else {
                    taskToSave.setId(currentTask.getId());
                    contentLayeredPane.getMainFrame().getTaskMessageProducer().sendUpdateTaskMessage(taskToSave);
                }
            }
        });
        add(submitButton, new GridBagConstraints(
                0, 5, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(topMargin, leftMargin, bottomMargin, rightMargin), 50, 20));
    }

    private void initTaskTitleRow() {
        titleLabel = new JLabel("Заголовок:");
        titleField = new JTextField();
        titleField.setColumns(40);
        titleField.setCaretColor(MainFrame.secondColor);
        titleField.setHorizontalAlignment(SwingConstants.CENTER);
        MainFrame.setBasicInterface(titleField);

        formLines.put(titleLabel, titleField);
    }

    private void initPerformerRow() {
        performerLabel = new JLabel("Исполнитель:");
        performerBox = new JComboBox();
        ((JLabel) performerBox.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        MainFrame.setBasicInterface(performerBox);


        formLines.put(performerLabel, performerBox);
    }

    private void initPriorityRow() {
        priorityLabel = new JLabel("Приоритет:");
        priorityBox = new JComboBox();
        ((JLabel) priorityBox.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        MainFrame.setBasicInterface(priorityBox);
        for (TaskPriority priority : TaskPriority.values()) {
            priorityBox.addItem(priority.getRuString());
        }

        formLines.put(priorityLabel, priorityBox);
    }

    private void initDeadlineRow() {
        deadlineDateLabel = new JLabel("Дедлайн:");
        dateTimePicker = new DateTimePicker();

        dateTimePicker.getDatePicker().setBorder(MainFrame.border);
        dateTimePicker.getDatePicker().getSettings().setFontValidDate(MainFrame.font);
        dateTimePicker.getDatePicker().getSettings().setFontInvalidDate(MainFrame.font);
        dateTimePicker.getDatePicker().getComponentDateTextField().setHorizontalAlignment(SwingConstants.CENTER);

        dateTimePicker.getTimePicker().setBorder(MainFrame.border);
        dateTimePicker.getTimePicker().getSettings().fontValidTime = MainFrame.font;
        dateTimePicker.getTimePicker().getSettings().fontInvalidTime = MainFrame.font;
        dateTimePicker.getTimePicker().getComponentTimeTextField().setHorizontalAlignment(SwingConstants.CENTER);

        formLines.put(deadlineDateLabel, dateTimePicker);
    }

    private void initDescriptionRow() {
        descriptionLabel = new JLabel("Описание:");
        descriptionArea = new JTextArea();
        descriptionArea.setColumns(40);
        descriptionArea.setRows(8);
        MainFrame.setBasicInterface(descriptionArea);

        formLines.put(descriptionLabel, descriptionArea);
    }

    /** Заполнение формы данными редактируемой задачи */
    public void fillTaskForm(Task task) {
        titleField.setText(task.getTitle());
        User user = task.getPerformer();
        performerBox.setSelectedItem(user.getEmail() + " (" + user.getName() + " " + user.getSurname() + ")");
        priorityBox.setSelectedItem(task.getPriority().getRuString());
        dateTimePicker.getDatePicker().setDate(task.getDeadline().toLocalDate());
        dateTimePicker.getTimePicker().setTime(task.getDeadline().toLocalTime());
        descriptionArea.setText(task.getDescription());
    }

    /** Очистка формы от данных */
    public void clearTaskForm() {
        titleField.setText("");
        performerBox.setSelectedItem(null);
        priorityBox.setSelectedItem(null);
        dateTimePicker.getDatePicker().setDate(null);
        dateTimePicker.getTimePicker().setTime(null);
        descriptionArea.setText("");
    }

    /** Обновление списка пользователей в выпадающем списке для выбора исполнителя */
    public void updatePerformerBoxItems(List<User> users) {
        performerBox.removeAllItems();
        for (User user : users) {
            performerBox.addItem(user.getEmail() + " (" + user.getName() + " " + user.getSurname() + ")");
        }
    }

    /** Изменение текста на панели для апдейта задачи */
    public void rewriteToUpdate() {
        pageTitleLabel.setText("ИЗМЕНЕНИЕ ЗАДАЧИ");
        submitButton.setText("СОХРАНИТЬ ИЗМЕНЕНИЯ");
    }

    /** Изменение текста на панели для создания задачи */
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

    public String validateForm() {
        if (titleField.getText().isEmpty()) {
            return "Необходимо указать название задачи.";
        } else if (performerBox.getSelectedItem() == null) {
            return "Необходимо указать исполнителя задачи.";
        } else if (priorityBox.getSelectedItem() == null) {
            return "Необходимо указать приоритет задачи.";
        } else if (dateTimePicker.getDatePicker().getDate() == null || dateTimePicker.getTimePicker().getTime() == null) {
            return "Необходимо указать дату и время дедлайна.";
        }
        return "+";
    }
}
