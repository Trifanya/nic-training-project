package dev.trifanya.component;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class SortCriteriaPanel extends JPanel {
    private Color firstColor = Color.BLACK;
    private Color secondColor = Color.ORANGE;
    private Font font = new Font(Font.SANS_SERIF, Font.BOLD, 16);

    private JLabel sortCriteriaLabel = new JLabel();
    private JRadioButton deadlineRadio = new JRadioButton();
    private JRadioButton creationDateRadio = new JRadioButton();
    private JRadioButton ascDirRadio = new JRadioButton();
    private JRadioButton descDirRadio = new JRadioButton();

    public SortCriteriaPanel() {
        this.setLayout(new GridBagLayout());
        this.setBackground(firstColor);
        this.setBorder(new LineBorder(secondColor, 3, true));
        init();
    }

    public void init() {
        sortCriteriaLabel.setText(
                "<html><p style=\"text-align:center;\">Критерий<p style=\"text-align:center;\">сортировки<html>");
        sortCriteriaLabel.setPreferredSize(new Dimension(150, 70));
        sortCriteriaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        sortCriteriaLabel.setFont(font);
        sortCriteriaLabel.setOpaque(true);
        sortCriteriaLabel.setBackground(firstColor);
        sortCriteriaLabel.setForeground(secondColor);
        sortCriteriaLabel.setBorder(new LineBorder(secondColor, 3, true));
        this.add(sortCriteriaLabel, new GridBagConstraints(
                0, 0, 1, 1, 0, 0,
                GridBagConstraints.PAGE_START, GridBagConstraints.NONE,
                new Insets(10, 0, 10, 0), 0, 0));

        ButtonGroup sortCriteriaGroup = new ButtonGroup();

        deadlineRadio.setText("Дедлайн");
        deadlineRadio.setFont(font);
        deadlineRadio.setBackground(firstColor);
        deadlineRadio.setForeground(secondColor);
        deadlineRadio.setBorder(new LineBorder(secondColor, 3, true));
        sortCriteriaGroup.add(deadlineRadio);
        this.add(deadlineRadio, new GridBagConstraints(
                0, 1, 1, 1, 0, 0,
                GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL,
                new Insets(0, 5, 0, 5), 0, 0));

        creationDateRadio.setText("Дата создания");
        creationDateRadio.setFont(font);
        creationDateRadio.setBackground(firstColor);
        creationDateRadio.setForeground(secondColor);
        creationDateRadio.setBorder(new LineBorder(secondColor, 3, true));
        sortCriteriaGroup.add(creationDateRadio);
        this.add(creationDateRadio, new GridBagConstraints(
                0, 2, 1, 1, 0, 0,
                GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL,
                new Insets(0, 5, 0, 5), 0, 0));

        sortCriteriaGroup.setSelected(deadlineRadio.getModel(), true);

        ButtonGroup sortDirGroup = new ButtonGroup();

        ascDirRadio.setText("По возрастанию");
        ascDirRadio.setFont(font);
        ascDirRadio.setBackground(firstColor);
        ascDirRadio.setForeground(secondColor);
        ascDirRadio.setBorder(new LineBorder(secondColor, 3, true));
        sortDirGroup.add(ascDirRadio);
        this.add(ascDirRadio, new GridBagConstraints(
                0, 3, 1, 1, 0, 0,
                GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL,
                new Insets(15, 5, 0, 5), 0, 0));

        descDirRadio.setText("По убыванию");
        descDirRadio.setFont(font);
        descDirRadio.setBackground(firstColor);
        descDirRadio.setForeground(secondColor);
        descDirRadio.setBorder(new LineBorder(secondColor, 3, true));
        sortDirGroup.add(descDirRadio);
        this.add(descDirRadio, new GridBagConstraints(
                0, 4, 1, 1, 0, 0,
                GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL,
                new Insets(0, 5, 10, 5), 0, 0));

        sortDirGroup.setSelected(ascDirRadio.getModel(), true);
    }
}
