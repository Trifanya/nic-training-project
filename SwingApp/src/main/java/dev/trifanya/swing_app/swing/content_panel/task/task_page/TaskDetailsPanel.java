package dev.trifanya.swing_app.swing.content_panel.task.task_page;

import dev.trifanya.server_app.model.Task;
import dev.trifanya.swing_app.swing.MainFrame;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.util.Map;
import java.util.LinkedHashMap;

@Getter
@Setter
public class TaskDetailsPanel extends JPanel {
    private Task currentTask;

    private JLabel pageTitleLabel;
    private JPanel taskInfoPanel;

    private JLabel idLabel;
    private JLabel titleLabel;
    private JLabel authorLabel;
    private JLabel performerLabel;
    private JLabel priorityLabel;
    private JLabel statusLabel;
    private JLabel creationDateLabel;
    private JLabel deadlineLabel;
    private JLabel descriptionLabel;

    private JLabel idValueLabel;
    private JLabel titleValueLabel;
    private JLabel authorValueLabel;
    private JLabel performerValueLabel;
    private JLabel priorityValueLabel;
    private JLabel statusValueLabel;
    private JLabel creationDateValueLabel;
    private JLabel deadlineValueLabel;
    private JLabel descriptionValueLabel;

    private Map<JLabel, JLabel> panelLines = new LinkedHashMap<>();

    private final int leftMargin = 15;
    private final int rightMargin = 15;
    private final int topMargin = 10;
    private final int bottomMargin = 10;

    private final int labelGridWidth = 1;
    private final int labelGridHeight = 1;
    private final int valueGridWidth = 1;
    private final int valueGridHeight = 1;

    private final double labelWeightX = 0;
    private final double labelWeightY = 0;
    private final double valueWeightX = 1;
    private final double valueWeightY = 0;

    private final int ipadx = 10;
    private final int ipady = 15;

    public TaskDetailsPanel() {
        setLayout(new GridBagLayout());
        setMinimumSize(new Dimension(600, 400));
        setBackground(MainFrame.firstColor);
        setBorder(new LineBorder(MainFrame.secondColor, 3, true));
    }

    public void init() {
        initPageTitleLabel();
        initTaskInfoPanel();

        idLabel = new JLabel("ID:");
        titleLabel = new JLabel("Заголовок:");
        authorLabel = new JLabel("Автор:");
        performerLabel = new JLabel("Исполнитель:");
        statusLabel = new JLabel("Статус:");
        priorityLabel = new JLabel("Приоритет:");
        creationDateLabel = new JLabel("Дата создания:");
        deadlineLabel = new JLabel("Дедлайн:");
        descriptionLabel = new JLabel("Описание:");

        idValueLabel = new JLabel("");
        titleValueLabel = new JLabel("");
        authorValueLabel = new JLabel("");
        performerValueLabel = new JLabel("");
        statusValueLabel = new JLabel("");
        priorityValueLabel = new JLabel("");
        creationDateValueLabel = new JLabel();
        deadlineValueLabel = new JLabel("");
        descriptionValueLabel = new JLabel("");

        panelLines.put(idLabel, idValueLabel);
        panelLines.put(titleLabel, titleValueLabel);
        panelLines.put(authorLabel, authorValueLabel);
        panelLines.put(performerLabel, performerValueLabel);
        panelLines.put(statusLabel, statusValueLabel);
        panelLines.put(priorityLabel, priorityValueLabel);
        panelLines.put(creationDateLabel, creationDateValueLabel);
        panelLines.put(deadlineLabel, deadlineValueLabel);
        panelLines.put(descriptionLabel, descriptionValueLabel);

        int currentRow = 0;
        int currentColumn = 0;
        for (Map.Entry<JLabel, JLabel> panelLine : panelLines.entrySet()) {
            MainFrame.setBasicInterface(panelLine.getKey());
            panelLine.getKey().setBorder(null);
            panelLine.getKey().setHorizontalAlignment(SwingConstants.RIGHT);
            taskInfoPanel.add(panelLine.getKey(), new GridBagConstraints(
                    currentColumn++, currentRow, labelGridWidth, labelGridHeight, labelWeightX, labelWeightY,
                    GridBagConstraints.EAST, GridBagConstraints.NONE,
                    new Insets(topMargin, leftMargin, bottomMargin, rightMargin), ipadx, ipady));
            MainFrame.setBasicInterface(panelLine.getValue());
            panelLine.getValue().setHorizontalAlignment(SwingConstants.CENTER);
            taskInfoPanel.add(panelLine.getValue(), new GridBagConstraints(
                    currentColumn--, currentRow++, valueGridWidth, valueGridHeight, valueWeightX, valueWeightY,
                    GridBagConstraints.WEST, GridBagConstraints.BOTH,
                    new Insets(topMargin, leftMargin, bottomMargin, rightMargin), ipadx, ipady));

        }
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

    public void fill() {
        idValueLabel.setText(String.valueOf(currentTask.getId()));
        titleValueLabel.setText(currentTask.getTitle());
        authorValueLabel.setText(currentTask.getAuthor().getEmail());
        performerValueLabel.setText(currentTask.getPerformer().getEmail());
        priorityValueLabel.setText(currentTask.getPriority().getRuString());
        statusValueLabel.setText(currentTask.getStatus().getRuString());
        creationDateValueLabel.setText(currentTask.getCreatedAt().toString());
        deadlineValueLabel.setText(currentTask.getDeadline().toString());
        descriptionValueLabel.setText(currentTask.getDescription());
    }
}
