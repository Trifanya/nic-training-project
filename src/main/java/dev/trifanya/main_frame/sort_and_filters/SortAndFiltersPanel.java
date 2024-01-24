package dev.trifanya.main_frame.sort_and_filters;

import dev.trifanya.Main;
import dev.trifanya.main_frame.MainFrame;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class SortAndFiltersPanel extends JPanel {
    private SortCriteriaPanel sortCriteriaPanel;
    private FiltersPanel filtersPanel;

    public SortAndFiltersPanel() {
        setLayout(new GridBagLayout());
        MainFrame.setBasicInterface(this);
        setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        setPreferredSize(new Dimension(250, 0));
    }

    public void init() {
        sortCriteriaPanel = new SortCriteriaPanel();
        sortCriteriaPanel.init();
        add(sortCriteriaPanel, new GridBagConstraints(
                0, 0, 1, 1, 1, 0,
                GridBagConstraints.PAGE_START, GridBagConstraints.BOTH,
                new Insets(15, 20, 15, 20), 0, 0));

        filtersPanel = new FiltersPanel();
        filtersPanel.init();
        add(filtersPanel, new GridBagConstraints(
                0, 1, 1, 2, 1, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 20, 15, 20), 0, 0));
    }
}
