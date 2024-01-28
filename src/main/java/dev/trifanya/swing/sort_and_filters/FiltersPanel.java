package dev.trifanya.swing.sort_and_filters;

import dev.trifanya.model.TaskPriority;
import dev.trifanya.model.TaskStatus;
import dev.trifanya.service.TaskFiltersBuilder;
import dev.trifanya.swing.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class FiltersPanel extends JPanel {

    private SortAndFiltersPanel parent;

    private JLabel filtersLabel;
    private JCheckBox lowPriorityBox;
    private JCheckBox mediumPriorityBox;
    private JCheckBox highPriorityBox;
    private JCheckBox notStartedStatusBox;
    private JCheckBox inProgressStatusBox;
    private JCheckBox completedStatusBox;
    private JButton applyFiltersButton;

    public FiltersPanel() {
        setLayout(new GridBagLayout());
        MainFrame.setBasicInterface(this);
    }

    public void init(SortAndFiltersPanel sortAndFiltersPanel) {
        this.parent = sortAndFiltersPanel;

        filtersLabel = new JLabel("Фильтры");
        filtersLabel.setPreferredSize(new Dimension(150, 40));
        filtersLabel.setHorizontalAlignment(SwingConstants.CENTER);
        filtersLabel.setOpaque(true);
        MainFrame.setBasicInterface(filtersLabel);
        add(filtersLabel, new GridBagConstraints(
                0, 0, 1, 1, 0, 0,
                GridBagConstraints.PAGE_START, GridBagConstraints.NONE,
                new Insets(10, 0, 10, 0), 0, 10));

        lowPriorityBox = new JCheckBox("Низкий приоритет");
        MainFrame.setBasicInterface(lowPriorityBox);
        lowPriorityBox.setSelected(true);
        add(lowPriorityBox, new GridBagConstraints(
                0, 1, 1, 1, 0, 0,
                GridBagConstraints.PAGE_START, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0
        ));

        mediumPriorityBox = new JCheckBox("Средний приоритет");
        MainFrame.setBasicInterface(mediumPriorityBox);
        mediumPriorityBox.setSelected(true);
        add(mediumPriorityBox, new GridBagConstraints(
                0, 2, 1, 1, 0, 0,
                GridBagConstraints.PAGE_START, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0
        ));

        highPriorityBox = new JCheckBox("Высокий приоритет");
        MainFrame.setBasicInterface(highPriorityBox);
        highPriorityBox.setSelected(true);
        add(highPriorityBox, new GridBagConstraints(
                0, 3, 1, 1, 0, 0,
                GridBagConstraints.PAGE_START, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0
        ));

        notStartedStatusBox = new JCheckBox("Не начата");
        MainFrame.setBasicInterface(notStartedStatusBox);
        notStartedStatusBox.setSelected(true);
        add(notStartedStatusBox, new GridBagConstraints(
                0, 4, 1, 1, 0, 0,
                GridBagConstraints.PAGE_START, GridBagConstraints.BOTH,
                new Insets(15, 0, 0, 0), 0, 0
        ));

        inProgressStatusBox = new JCheckBox("В процессе");
        MainFrame.setBasicInterface(inProgressStatusBox);
        inProgressStatusBox.setSelected(true);
        add(inProgressStatusBox, new GridBagConstraints(
                0, 5, 1, 1, 0, 0,
                GridBagConstraints.PAGE_START, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0
        ));

        completedStatusBox = new JCheckBox("Завершена");
        MainFrame.setBasicInterface(completedStatusBox);
        completedStatusBox.setSelected(true);
        add(completedStatusBox, new GridBagConstraints(
                0, 6, 1, 1, 0, 0,
                GridBagConstraints.PAGE_START, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0
        ));

        applyFiltersButton = new JButton("ПРИМЕНИТЬ");
        MainFrame.setBasicInterface(applyFiltersButton);
        applyFiltersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<String, String> filters = new HashMap<>();

                StringBuilder priorityValues = new StringBuilder();
                if (lowPriorityBox.isSelected()) priorityValues.append(TaskPriority.LOW);
                if (mediumPriorityBox.isSelected()) priorityValues.append("," + TaskPriority.MEDIUM);
                if (highPriorityBox.isSelected()) priorityValues.append("," + TaskPriority.HIGH);
                filters.put("priorityValues", priorityValues.toString());

                StringBuilder statusValues = new StringBuilder();
                if (notStartedStatusBox.isSelected()) statusValues.append(TaskStatus.NOT_STARTED);
                if (inProgressStatusBox.isSelected()) statusValues.append("," + TaskStatus.IN_PROGRESS);
                if (completedStatusBox.isSelected()) statusValues.append("," + TaskStatus.COMPLETED);
                filters.put("statusValues", statusValues.toString());

                parent.getContentLayeredPane().getTaskListPanel().setTaskFilters(filters);
            }
        });
        add(applyFiltersButton, new GridBagConstraints(
                0, 7, 1, 1, 0, 0,
                GridBagConstraints.PAGE_START, GridBagConstraints.BOTH,
                new Insets(0, 0, 10, 0), 0, 0
        ));
    }
}
