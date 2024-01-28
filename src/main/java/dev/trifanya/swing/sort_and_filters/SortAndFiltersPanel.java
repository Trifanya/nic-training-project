package dev.trifanya.swing.sort_and_filters;

import dev.trifanya.swing.MainFrame;
import dev.trifanya.swing.content.ContentLayeredPane;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public class SortAndFiltersPanel extends JPanel {
    private ContentLayeredPane contentLayeredPane;
    private SortCriteriaPanel sortCriteriaPanel;
    private FiltersPanel filtersPanel;

    public SortAndFiltersPanel() {
        setLayout(new GridBagLayout());
        MainFrame.setBasicInterface(this);
        setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        setPreferredSize(new Dimension(250, 0));
    }

    public void init(ContentLayeredPane contentLayeredPane) {
        this.contentLayeredPane = contentLayeredPane;

        sortCriteriaPanel = new SortCriteriaPanel();
        sortCriteriaPanel.init(this);
        add(sortCriteriaPanel, new GridBagConstraints(
                0, 0, 1, 1, 1, 0,
                GridBagConstraints.PAGE_START, GridBagConstraints.BOTH,
                new Insets(0, 20,15, 20), 0, 0));

        filtersPanel = new FiltersPanel();
        filtersPanel.init(this);
        add(filtersPanel, new GridBagConstraints(
                0, 1, 1, 1, 1, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 20, 0, 20), 0, 0));
    }
}
