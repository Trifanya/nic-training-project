package dev.trifanya.swing.sort_and_filters;

import dev.trifanya.swing.MainFrame;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class SortCriteriaPanel extends JPanel {
    private SortAndFiltersPanel parent;

    private JLabel sortCriteriaLabel;
    private JRadioButton deadlineRadio;
    private JRadioButton creationDateRadio;
    private JRadioButton ascDirRadio;
    private JRadioButton descDirRadio;

    public SortCriteriaPanel() {
        setLayout(new GridBagLayout());
        setBackground(MainFrame.firstColor);
        setBorder(new LineBorder(MainFrame.secondColor, 3, true));
    }

    public void init(SortAndFiltersPanel parent) {
        this.parent = parent;

        sortCriteriaLabel = new JLabel(
                "<html><p style=\"text-align:center;\">Критерий<p style=\"text-align:center;\">сортировки<html>");
        sortCriteriaLabel.setPreferredSize(new Dimension(150, 70));
        sortCriteriaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        sortCriteriaLabel.setOpaque(true);
        MainFrame.setBasicInterface(sortCriteriaLabel);
        add(sortCriteriaLabel, new GridBagConstraints(
                0, 0, 1, 1, 0, 0,
                GridBagConstraints.PAGE_START, GridBagConstraints.NONE,
                new Insets(10, 0, 10, 0), 0, 0));

        ButtonGroup sortCriteriaGroup = new ButtonGroup();

        deadlineRadio = new JRadioButton("Дедлайн");
        MainFrame.setBasicInterface(deadlineRadio);
        sortCriteriaGroup.add(deadlineRadio);
        add(deadlineRadio, new GridBagConstraints(
                0, 1, 1, 1, 0, 0,
                GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL,
                new Insets(0, 5, 0, 5), 0, 0));

        creationDateRadio = new JRadioButton("Дата создания");
        MainFrame.setBasicInterface(creationDateRadio);
        sortCriteriaGroup.add(creationDateRadio);
        add(creationDateRadio, new GridBagConstraints(
                0, 2, 1, 1, 0, 0,
                GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL,
                new Insets(0, 5, 0, 5), 0, 0));

        sortCriteriaGroup.setSelected(deadlineRadio.getModel(), true);

        ButtonGroup sortDirGroup = new ButtonGroup();

        ascDirRadio = new JRadioButton("По возрастанию");
        MainFrame.setBasicInterface(ascDirRadio);
        sortDirGroup.add(ascDirRadio);
        add(ascDirRadio, new GridBagConstraints(
                0, 3, 1, 1, 0, 0,
                GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL,
                new Insets(15, 5, 0, 5), 0, 0));

        descDirRadio = new JRadioButton("По убыванию");
        MainFrame.setBasicInterface(descDirRadio);
        sortDirGroup.add(descDirRadio);
        add(descDirRadio, new GridBagConstraints(
                0, 4, 1, 1, 0, 0,
                GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL,
                new Insets(0, 5, 10, 5), 0, 0));

        sortDirGroup.setSelected(ascDirRadio.getModel(), true);
    }
}
