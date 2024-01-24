package dev.trifanya.main_frame.sort_and_filters;

import dev.trifanya.Main;
import dev.trifanya.main_frame.MainFrame;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

@Component
public class FiltersPanel extends JPanel {
    private JLabel filtersLabel;
    private JCheckBox lowPriorityBox;
    private JCheckBox mediumPriorityBox;
    private JCheckBox highPriorityBox;
    private JCheckBox notStartedStatusBox;
    private JCheckBox inProgressStatusBox;
    private JCheckBox completedStatusBox;

    public FiltersPanel() {
        setLayout(new GridBagLayout());
        MainFrame.setBasicInterface(this);
    }

    public void init() {
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
    }
}
