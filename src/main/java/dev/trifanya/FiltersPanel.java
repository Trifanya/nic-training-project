package dev.trifanya;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class FiltersPanel extends JPanel {
    private Color firstColor = Color.BLACK;
    private Color secondColor = Color.ORANGE;
    private Font font = new Font(Font.SANS_SERIF, Font.BOLD, 16);

    private JLabel filtersLabel = new JLabel();
    private JCheckBox lowPriorityBox = new JCheckBox();
    private JCheckBox mediumPriorityBox = new JCheckBox();
    private JCheckBox highPriorityBox = new JCheckBox();
    private JCheckBox notStartedStatusBox = new JCheckBox();
    private JCheckBox inProgressStatusBox = new JCheckBox();
    private JCheckBox completedStatusBox = new JCheckBox();

    public FiltersPanel() {
        this.setLayout(new GridBagLayout());
        this.setBackground(firstColor);
        this.setBorder(new LineBorder(secondColor, 3, true));
        this.setFont(font);
        init();
    }

    public void init() {
        filtersLabel.setText("Фильтры");
        filtersLabel.setPreferredSize(new Dimension(150, 40));
        filtersLabel.setHorizontalAlignment(SwingConstants.CENTER);
        filtersLabel.setFont(font);
        filtersLabel.setOpaque(true);
        filtersLabel.setBackground(firstColor);
        filtersLabel.setForeground(secondColor);
        filtersLabel.setBorder(new LineBorder(secondColor, 3, true));
        this.add(filtersLabel, new GridBagConstraints(
                0, 0, 1, 1, 0, 0,
                GridBagConstraints.PAGE_START, GridBagConstraints.NONE,
                new Insets(10, 0, 10, 0), 0, 10));

        lowPriorityBox.setText("Низкий приоритет");
        lowPriorityBox.setFont(font);
        lowPriorityBox.setBackground(firstColor);
        lowPriorityBox.setForeground(secondColor);
        lowPriorityBox.setBorder(new LineBorder(secondColor, 3, true));
        lowPriorityBox.setSelected(true);
        this.add(lowPriorityBox, new GridBagConstraints(
                0, 1, 1, 1, 0, 0,
                GridBagConstraints.PAGE_START, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0
        ));

        mediumPriorityBox.setText("Средний приоритет");
        mediumPriorityBox.setFont(font);
        mediumPriorityBox.setBackground(firstColor);
        mediumPriorityBox.setForeground(secondColor);
        mediumPriorityBox.setBorder(new LineBorder(secondColor, 3, true));
        mediumPriorityBox.setSelected(true);
        this.add(mediumPriorityBox, new GridBagConstraints(
                0, 2, 1, 1, 0, 0,
                GridBagConstraints.PAGE_START, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0
        ));

        highPriorityBox.setText("Высокий приоритет");
        highPriorityBox.setFont(font);
        highPriorityBox.setBackground(firstColor);
        highPriorityBox.setForeground(secondColor);
        highPriorityBox.setBorder(new LineBorder(secondColor, 3, true));
        highPriorityBox.setSelected(true);
        this.add(highPriorityBox, new GridBagConstraints(
                0, 3, 1, 1, 0, 0,
                GridBagConstraints.PAGE_START, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0
        ));

        notStartedStatusBox.setText("Не начата");
        notStartedStatusBox.setFont(font);
        notStartedStatusBox.setBackground(firstColor);
        notStartedStatusBox.setForeground(secondColor);
        notStartedStatusBox.setBorder(new LineBorder(secondColor, 3, true));
        notStartedStatusBox.setSelected(true);
        this.add(notStartedStatusBox, new GridBagConstraints(
                0, 4, 1, 1, 0, 0,
                GridBagConstraints.PAGE_START, GridBagConstraints.BOTH,
                new Insets(15, 0, 0, 0), 0, 0
        ));

        inProgressStatusBox.setText("В процессе");
        inProgressStatusBox.setFont(font);
        inProgressStatusBox.setBackground(firstColor);
        inProgressStatusBox.setForeground(secondColor);
        inProgressStatusBox.setBorder(new LineBorder(secondColor, 3, true));
        inProgressStatusBox.setSelected(true);
        this.add(inProgressStatusBox, new GridBagConstraints(
                0, 5, 1, 1, 0, 0,
                GridBagConstraints.PAGE_START, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0
        ));

        completedStatusBox.setText("Завершена");
        completedStatusBox.setFont(font);
        completedStatusBox.setBackground(firstColor);
        completedStatusBox.setForeground(secondColor);
        completedStatusBox.setBorder(new LineBorder(secondColor, 3, true));
        completedStatusBox.setSelected(true);
        this.add(completedStatusBox, new GridBagConstraints(
                0, 6, 1, 1, 0, 0,
                GridBagConstraints.PAGE_START, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0
        ));
    }
}
