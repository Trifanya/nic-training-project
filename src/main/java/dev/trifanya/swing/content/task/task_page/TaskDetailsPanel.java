package dev.trifanya.swing.content.task.task_page;

import dev.trifanya.model.Task;
import dev.trifanya.swing.MainFrame;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

@Setter
@Getter
public class TaskDetailsPanel extends JPanel {
    private final int leftMargin = 10;
    private final int rightMargin = 10;
    private final int topMargin = 10;
    private final int bottomMargin = 10;

    private int currentRow = 0;
    private int currentColumn = 0;

    private final int labelHorizontalAlignment = SwingConstants.LEFT;

    private Task currentTask;

    private JLabel pageTitleLabel;

    private JPanel taskInfoPanel;

    private JLabel idLabel;
    private JLabel taskId;

    private JLabel titleLabel;
    private JLabel taskTitle;

    private JLabel authorLabel;
    private JLabel taskAuthor;

    private JLabel performerLabel;
    private JLabel taskPerformer;

    private JLabel priorityLabel;
    private JLabel taskPriority;

    private JLabel statusLabel;
    private JLabel taskStatus;

    private JLabel creationDateLabel;
    private JLabel taskCreationDate;

    private JLabel deadlineLabel;
    private JLabel taskDeadline;

    private JLabel descriptionLabel;
    private JLabel taskDescription;

    public TaskDetailsPanel() {
        setLayout(new GridBagLayout());
        setMinimumSize(new Dimension(600, 400));
        setBackground(MainFrame.firstColor);
        setBorder(new LineBorder(MainFrame.secondColor, 3, true));
    }

    public void init() {
        initPageTitleLabel();
        initTaskInfoPanel();
        initIdRow();
        initTaskTitleRow();
        initAuthorRow();
        initPerformerRow();
        initStatusRow();
        initPriorityRow();
        //initCreationDateRow();
        initDeadlineRow();
        initDescriptionRow();
    }


    private void initPageTitleLabel() {
        pageTitleLabel = new JLabel("ИНФОРМАЦИЯ О ЗАДАЧЕ");
        MainFrame.setBasicInterface(pageTitleLabel);
        pageTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(pageTitleLabel, new GridBagConstraints(
                0, 0, 1, 1, 0, 0,
                GridBagConstraints.NORTH, GridBagConstraints.NONE,
                new Insets(topMargin, leftMargin, bottomMargin, rightMargin), 30, 20));
    }

    private void initTaskInfoPanel() {
        taskInfoPanel = new JPanel();
        taskInfoPanel.setLayout(new GridBagLayout());
        MainFrame.setBasicInterface(taskInfoPanel);
        add(taskInfoPanel, new GridBagConstraints(
                0, 1, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 25, 25, 25), 0, 0));
    }

    private void initIdRow() {
        idLabel = new JLabel("ID:");
        MainFrame.setBasicInterface(idLabel);
        idLabel.setHorizontalAlignment(labelHorizontalAlignment);
        taskInfoPanel.add(idLabel, new GridBagConstraints(
                currentColumn++, currentRow, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(topMargin, leftMargin, bottomMargin, rightMargin), 10, 10));

        taskId = new JLabel("");
        MainFrame.setBasicInterface(taskId);
        taskId.setHorizontalAlignment(SwingConstants.LEFT);
        taskInfoPanel.add(taskId, new GridBagConstraints(
                currentColumn--, currentRow++, 2, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(topMargin, leftMargin, bottomMargin, rightMargin), 10, 10));
    }

    private void initTaskTitleRow() {
        titleLabel = new JLabel("Заголовок:");
        MainFrame.setBasicInterface(titleLabel);
        titleLabel.setHorizontalAlignment(labelHorizontalAlignment);
        taskInfoPanel.add(titleLabel, new GridBagConstraints(
                currentColumn++, currentRow, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(topMargin, leftMargin, bottomMargin, rightMargin), 10, 10));

        taskTitle = new JLabel("");
        MainFrame.setBasicInterface(taskTitle);
        taskTitle.setHorizontalAlignment(SwingConstants.LEFT);
        taskInfoPanel.add(taskTitle, new GridBagConstraints(
                currentColumn--, currentRow++, 2, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(topMargin, leftMargin, bottomMargin, rightMargin), 10, 10));
    }

    private void initAuthorRow() {
        authorLabel = new JLabel("Автор:");
        MainFrame.setBasicInterface(authorLabel);
        authorLabel.setHorizontalAlignment(labelHorizontalAlignment);
        taskInfoPanel.add(authorLabel, new GridBagConstraints(
                currentColumn++, currentRow, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(topMargin, leftMargin, bottomMargin, rightMargin), 10, 10));

        taskAuthor = new JLabel("");
        MainFrame.setBasicInterface(taskAuthor);
        taskAuthor.setHorizontalAlignment(SwingConstants.LEFT);
        taskInfoPanel.add(taskAuthor, new GridBagConstraints(
                currentColumn--, currentRow++, 2, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(topMargin, leftMargin, bottomMargin, rightMargin), 10, 10));
    }

    private void initPerformerRow() {
        performerLabel = new JLabel("Исполнитель:");
        MainFrame.setBasicInterface(performerLabel);
        performerLabel.setHorizontalAlignment(labelHorizontalAlignment);
        taskInfoPanel.add(performerLabel, new GridBagConstraints(
                currentColumn++, currentRow, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(topMargin, leftMargin, bottomMargin, rightMargin), 10, 10));

        taskPerformer = new JLabel("");
        MainFrame.setBasicInterface(taskPerformer);
        taskPerformer.setHorizontalAlignment(SwingConstants.LEFT);
        taskInfoPanel.add(taskPerformer, new GridBagConstraints(
                currentColumn--, currentRow++, 2, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(topMargin, leftMargin, bottomMargin, rightMargin), 10, 10));
    }

    private void initStatusRow() {
        statusLabel = new JLabel("Статус:");
        MainFrame.setBasicInterface(statusLabel);
        statusLabel.setHorizontalAlignment(labelHorizontalAlignment);
        taskInfoPanel.add(statusLabel, new GridBagConstraints(
                currentColumn++, currentRow, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(topMargin, leftMargin, bottomMargin, rightMargin), 10, 10));

        taskStatus = new JLabel("");
        MainFrame.setBasicInterface(taskStatus);
        taskStatus.setHorizontalAlignment(SwingConstants.LEFT);
        taskInfoPanel.add(taskStatus, new GridBagConstraints(
                currentColumn--, currentRow++, 2, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(topMargin, leftMargin, bottomMargin, rightMargin), 10, 10));
    }

    private void initPriorityRow() {
        priorityLabel = new JLabel("Приоритет:");
        MainFrame.setBasicInterface(priorityLabel);
        priorityLabel.setHorizontalAlignment(labelHorizontalAlignment);
        taskInfoPanel.add(priorityLabel, new GridBagConstraints(
                currentColumn++, currentRow, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(topMargin, leftMargin, bottomMargin, rightMargin), 10, 10));

        taskPriority = new JLabel("");
        MainFrame.setBasicInterface(taskPriority);
        taskPriority.setHorizontalAlignment(SwingConstants.LEFT);
        taskInfoPanel.add(taskPriority, new GridBagConstraints(
                currentColumn--, currentRow++, 2, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(topMargin, leftMargin, bottomMargin, rightMargin), 10, 10));
    }

    /*private void initCreationDateRow() {
        creationDateLabel = new JLabel("Дата создания:");
        MainFrame.setBasicInterface(creationDateLabel);
        creationDateLabel.setHorizontalAlignment(labelHorizontalAlignment);
        taskInfoPanel.add(creationDateLabel, new GridBagConstraints(
                currentColumn++, currentRow, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(topMargin, leftMargin, bottomMargin, rightMargin), 10, 10));

        taskCreateionDate = new JLabel();
        MainFrame.setBasicInterface(taskCreateionDate);
        taskCreateionDate.setHorizontalAlignment(SwingConstants.LEFT);
        taskInfoPanel.add(taskCreateionDate, new GridBagConstraints(
                currentColumn--, currentRow++, 2, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(topMargin, leftMargin, bottomMargin, rightMargin), 10, 10));
    }*/

    private void initDeadlineRow() {
        deadlineLabel = new JLabel("Дедлайн:");
        MainFrame.setBasicInterface(deadlineLabel);
        deadlineLabel.setHorizontalAlignment(labelHorizontalAlignment);
        taskInfoPanel.add(deadlineLabel, new GridBagConstraints(
                currentColumn++, currentRow, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(topMargin, leftMargin, bottomMargin, rightMargin), 10, 10));

        taskDeadline = new JLabel("");
        MainFrame.setBasicInterface(taskDeadline);
        taskDeadline.setHorizontalAlignment(SwingConstants.LEFT);
        taskInfoPanel.add(taskDeadline, new GridBagConstraints(
                currentColumn--, currentRow++, 2, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(topMargin, leftMargin, bottomMargin, rightMargin), 10, 10));
    }

    private void initDescriptionRow() {
        descriptionLabel = new JLabel("Описание:");
        MainFrame.setBasicInterface(descriptionLabel);
        descriptionLabel.setHorizontalAlignment(labelHorizontalAlignment);
        taskInfoPanel.add(descriptionLabel, new GridBagConstraints(
                currentColumn++, currentRow, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(topMargin, leftMargin, bottomMargin, rightMargin), 10, 10));

        taskDescription = new JLabel("");
        MainFrame.setBasicInterface(taskDescription);
        taskDescription.setHorizontalAlignment(SwingConstants.LEFT);
        taskInfoPanel.add(taskDescription, new GridBagConstraints(
                currentColumn--, currentRow++, 2, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(topMargin, leftMargin, bottomMargin, rightMargin), 10, 10));
    }

    public void fill() {
        taskId.setText(String.valueOf(currentTask.getId()));
        taskTitle.setText(currentTask.getTitle());
        taskAuthor.setText(currentTask.getAuthor().getEmail());
        taskPerformer.setText(currentTask.getPerformer().getEmail());
        taskPriority.setText(currentTask.getPriority().getRuString());
        taskStatus.setText(currentTask.getStatus().getRuString());
        //taskCreationDate.setText(currentTask.getPerformerEmail());
        taskDeadline.setText(currentTask.getDeadline().toString());
        taskDescription.setText(currentTask.getDescription());
    }
}