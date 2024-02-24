package dev.trifanya.swing_app.swing.sort_and_filters_panel;

import dev.trifanya.swing_app.swing.MainFrame;
import dev.trifanya.swing_app.swing.content_panel.ContentLayeredPane;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.Map;
import java.util.HashMap;

@Getter
@Setter
public class SortAndFiltersPanel extends JPanel {
    private MainFrame mainFrame;

    private ContentLayeredPane contentLayeredPane;
    private SortCriteriaPanel sortCriteriaPanel;
    private FiltersPanel filtersPanel;

    private JButton applyFiltersButton;

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
                new Insets(0, 20, 15, 20), 0, 0));

        applyFiltersButton = new JButton("ПРИМЕНИТЬ");
        MainFrame.setBasicInterface(applyFiltersButton);
        applyFiltersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mainFrame.getTaskMessageProducer().sendGetTaskListMessage(getFilters());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        add(applyFiltersButton, new GridBagConstraints(
                0, 2, 1, 1, 1, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(15, 20, 0, 20), 0, 20
        ));
    }

    public Map<String, String> getFilters() {
        Map<String, String> filters = new HashMap<>();
        filters.putAll(sortCriteriaPanel.getSortParams());
        filters.putAll(filtersPanel.getFilters());
        return filters;
    }

    public void setContentVisibility(boolean isVisible) {
        sortCriteriaPanel.setVisible(isVisible);
        filtersPanel.setVisible(isVisible);
        applyFiltersButton.setVisible(isVisible);
    }
}
