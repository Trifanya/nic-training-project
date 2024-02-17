package dev.trifanya.swing_crudapp.swing.sort_and_filters_panel;

import dev.trifanya.swing_crudapp.swing.MainFrame;
import dev.trifanya.swing_crudapp.swing.content_panel.ContentLayeredPane;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public class SortAndFiltersPanel extends JPanel {
    private MainFrame mainFrame;

    private ContentLayeredPane contentLayeredPane;
    private SortCriteriaPanel sortCriteriaPanel;
    private FiltersPanel filtersPanel;

    public SortAndFiltersPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
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

    public void setContentVisible(boolean visible) {
        sortCriteriaPanel.setVisible(visible);
        filtersPanel.setVisible(visible);
    }
}
