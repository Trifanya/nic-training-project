package dev.trifanya.component;

import dev.trifanya.dto.TaskDTO;
import dev.trifanya.dto.TaskPriority;
import dev.trifanya.dto.TaskStatus;
import dev.trifanya.dto.UserDTO;
import dev.trifanya.server_connection.TaskClient;
import dev.trifanya.server_connection.UserClient;
import lombok.Data;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.Properties;

@Data
public class TaskFormPanel extends JPanel {
    private final UserClient userClient;
    private final TaskClient taskClient;

    private Color firstColor = Color.BLACK;
    private Color secondColor = Color.ORANGE;
    private Font font = new Font(Font.SANS_SERIF, Font.BOLD, 16);

    private final int leftMargin = 25;
    private final int rightMargin = 25;
    private final int topMargin = 25;
    private final int bottomMargin = 25;

    private int currentRow = 0;
    private int currentColumn = 0;

    private final int labelHorizontalAlignment = SwingConstants.LEFT;

    private JLabel taskCreationLabel = new JLabel();
    private JPanel dataPanel = new JPanel();

    private JLabel titleLabel = new JLabel();
    private JTextField titleField = new JTextField();

    private JLabel performerLabel = new JLabel();
    private JComboBox performerBox = new JComboBox();

    private JLabel priorityLabel = new JLabel();
    private JComboBox priorityBox = new JComboBox();

    private JLabel deadlineLabel = new JLabel();
    private JDatePickerImpl datePicker = null;

    private JLabel descriptionLabel = new JLabel();
    private JTextArea descriptionArea = new JTextArea();

    private JButton createTaskButton = new JButton();

    public TaskFormPanel() {
        this.taskClient = new TaskClient();
        this.userClient = new UserClient();
        setLayout(new GridBagLayout());
        //setPreferredSize(new Dimension(250, 0));
        setMinimumSize(new Dimension(600, 400));
        setBackground(firstColor);
        setBorder(new LineBorder(secondColor, 3, true));
    }

    public void init() {
        taskCreationLabel.setText("СОЗДАНИЕ НОВОЙ ЗАДАЧИ");
        taskCreationLabel.setBackground(firstColor);
        taskCreationLabel.setForeground(secondColor);
        taskCreationLabel.setFont(font);
        taskCreationLabel.setBorder(new LineBorder(secondColor, 3, true));
        taskCreationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(taskCreationLabel, new GridBagConstraints(
                0, 0, 1, 1, 0, 0,
                GridBagConstraints.NORTH, GridBagConstraints.NONE,
                new Insets(topMargin, leftMargin, bottomMargin, rightMargin), 30, 20));

        dataPanel.setLayout(new GridBagLayout());
        dataPanel.setBackground(firstColor);
        dataPanel.setForeground(secondColor);
        dataPanel.setBorder(new LineBorder(secondColor, 3, true));
        this.add(dataPanel, new GridBagConstraints(
                0, 1, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 25, 25, 25), 0, 0));

        titleRowInit();
        performerRowInit();
        priorityRowInit();
        deadlineRowInit();
        descriptionRowInit();

        createTaskButton.setText("СОЗДАТЬ ЗАДАЧУ");
        createTaskButton.setPreferredSize(new Dimension(200, 40));
        createTaskButton.setBackground(secondColor);
        createTaskButton.setForeground(firstColor);
        createTaskButton.setBorder(new LineBorder(secondColor, 3, true));
        createTaskButton.setFont(font);
        createTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDateTime testDeadline = LocalDateTime.now().plusDays(5);
                String performerEmail = performerBox.getSelectedItem().toString().split(" ")[0];

                TaskDTO taskToSave = new TaskDTO()
                        .setTitle(titleField.getText())
                        .setDescription(descriptionArea.getText())
                        .setPerformerEmail(performerEmail)
                        .setStatus(TaskStatus.NOT_STARTED)
                        .setPriority(TaskPriority.getTaskPriority(priorityBox.getSelectedItem().toString()))
                        .setDeadline(testDeadline);
                taskClient.createNewTask(taskToSave);
            }
        });
        this.add(createTaskButton, new GridBagConstraints(
                0, 5, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 50, 20));
    }

    private void titleRowInit() {
        titleLabel.setText("Заголовок:");
        titleLabel.setBackground(firstColor);
        titleLabel.setForeground(secondColor);
        titleLabel.setFont(font);
        titleLabel.setBorder(new LineBorder(secondColor, 3, true));
        titleLabel.setHorizontalAlignment(labelHorizontalAlignment);
        dataPanel.add(titleLabel, new GridBagConstraints(currentColumn++, currentRow, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(topMargin, leftMargin, bottomMargin, rightMargin), 10, 10));

        titleField.setBackground(firstColor);
        titleField.setColumns(40);
        titleField.setForeground(secondColor);
        titleField.setFont(font);
        titleField.setBorder(new LineBorder(secondColor, 3, true));
        titleField.setCaretColor(secondColor);
        titleField.setHorizontalAlignment(SwingConstants.LEFT);
        dataPanel.add(titleField, new GridBagConstraints(currentColumn--, currentRow++, 2, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(topMargin, leftMargin, bottomMargin, rightMargin), 10, 10));
    }

    private void performerRowInit() {
        performerLabel.setText("Исполнитель:");
        performerLabel.setBackground(firstColor);
        performerLabel.setForeground(secondColor);
        performerLabel.setFont(font);
        performerLabel.setBorder(new LineBorder(secondColor, 3, true));
        performerLabel.setHorizontalAlignment(labelHorizontalAlignment);
        dataPanel.add(performerLabel, new GridBagConstraints(currentColumn++, currentRow, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 10, 10));

        performerBox.setBackground(firstColor);
        performerBox.setForeground(secondColor);
        performerBox.setFont(font);
        performerBox.setBorder(new LineBorder(secondColor, 3, true));
        for (UserDTO user : userClient.getAllUsers()) {
            performerBox.addItem(user.getEmail()+ " (" + user.getName() + " " + user.getSurname() + ")");
        }
        dataPanel.add(performerBox, new GridBagConstraints(currentColumn--, currentRow++, 2, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 10, 10));
    }

    private void priorityRowInit() {
        priorityLabel.setText("Приоритет:");
        priorityLabel.setBackground(firstColor);
        priorityLabel.setForeground(secondColor);
        priorityLabel.setFont(font);
        priorityLabel.setBorder(new LineBorder(secondColor, 3, true));
        priorityLabel.setHorizontalAlignment(labelHorizontalAlignment);
        dataPanel.add(priorityLabel, new GridBagConstraints(currentColumn++, currentRow, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 10, 10));

        priorityBox.setBackground(firstColor);
        priorityBox.setForeground(secondColor);
        priorityBox.setFont(font);
        priorityBox.setBorder(new LineBorder(secondColor, 3, true));
        for (TaskPriority priority : TaskPriority.values()) {
            priorityBox.addItem(priority.getRuString());
        }
        dataPanel.add(priorityBox, new GridBagConstraints(currentColumn--, currentRow++, 1, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 10, 10));
    }

    private void deadlineRowInit() {
        deadlineLabel.setText("Дедлайн:");
        deadlineLabel.setBackground(firstColor);
        deadlineLabel.setForeground(secondColor);
        deadlineLabel.setFont(font);
        deadlineLabel.setBorder(new LineBorder(secondColor, 3, true));
        deadlineLabel.setHorizontalAlignment(labelHorizontalAlignment);
        dataPanel.add(deadlineLabel, new GridBagConstraints(currentColumn++, currentRow, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 10, 10));

        UtilDateModel dateModel = new UtilDateModel();
        dateModel.setDate(2024, 0, 1);
        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, new Properties());
        datePicker = new JDatePickerImpl(datePanel, new DateFormatter());
        datePicker.setBackground(firstColor);
        datePicker.setForeground(secondColor);
        datePicker.setFont(font);
        datePicker.setBorder(new LineBorder(secondColor, 3, true));
        dataPanel.add(datePicker, new GridBagConstraints(currentColumn--, currentRow++, 2, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 10, 10));
    }

    private void descriptionRowInit() {
        descriptionLabel.setText("Описание:");
        descriptionLabel.setBackground(firstColor);
        descriptionLabel.setForeground(secondColor);
        descriptionLabel.setFont(font);
        descriptionLabel.setBorder(new LineBorder(secondColor, 3, true));
        descriptionLabel.setHorizontalAlignment(labelHorizontalAlignment);
        descriptionLabel.setVerticalAlignment(SwingConstants.TOP);
        dataPanel.add(descriptionLabel, new GridBagConstraints(currentColumn++, currentRow, 1, 1, 0, 0,
                GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 10, 10));

        descriptionArea.setBackground(firstColor);
        descriptionArea.setColumns(40);
        descriptionArea.setRows(8);
        descriptionArea.setForeground(secondColor);
        descriptionArea.setFont(font);
        descriptionArea.setBorder(new LineBorder(secondColor, 3, true));
        dataPanel.add(descriptionArea, new GridBagConstraints(currentColumn--, currentRow++, 2, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, leftMargin, bottomMargin, rightMargin), 10, 10));
    }
}
