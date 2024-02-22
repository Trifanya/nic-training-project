package dev.trifanya.swing_app.swing.sort_and_filters_panel;

import dev.trifanya.server_app.model.TaskPriority;
import dev.trifanya.server_app.model.TaskStatus;
import dev.trifanya.swing_app.swing.MainFrame;
import dev.trifanya.swing_app.swing.content_panel.ContentLayeredPane;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class SortAndFiltersPanel extends JPanel {
    private MainFrame mainFrame;

    private ContentLayeredPane contentLayeredPane;
    private SortCriteriaPanel sortCriteriaPanel;
    private FiltersPanel filtersPanel;

    private JButton applyFiltersButton;

    private Map<String, String> filters = new HashMap<>();

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

        applyFiltersButton = new JButton("ПРИМЕНИТЬ");
        MainFrame.setBasicInterface(applyFiltersButton);
        applyFiltersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<String, String> filters = new HashMap<>();

                StringBuilder priorityValues = new StringBuilder();
                if (filtersPanel.getLowPriorityBox().isSelected()) priorityValues.append(TaskPriority.LOW);
                if (filtersPanel.getMediumPriorityBox().isSelected()) priorityValues.append("," + TaskPriority.MEDIUM);
                if (filtersPanel.getHighPriorityBox().isSelected()) priorityValues.append("," + TaskPriority.HIGH);
                filters.put("priorityValues", priorityValues.toString());

                StringBuilder statusValues = new StringBuilder();
                if (filtersPanel.getNotStartedStatusBox().isSelected()) statusValues.append(TaskStatus.NOT_STARTED);
                if (filtersPanel.getInProgressStatusBox().isSelected()) statusValues.append("," + TaskStatus.IN_PROGRESS);
                if (filtersPanel.getCompletedStatusBox().isSelected()) statusValues.append("," + TaskStatus.COMPLETED);
                filters.put("statusValues", statusValues.toString());

                String sortByColumn;
                if (sortCriteriaPanel.getIdRadio().isSelected()) {
                    sortByColumn = "id";
                } else if (sortCriteriaPanel.getDeadlineRadio().isSelected()) {
                    sortByColumn = "deadline";
                } else {
                    sortByColumn = "created_at";
                }
                filters.put("sortByColumn", sortByColumn);

                String sortDir = null;
                if (sortCriteriaPanel.getAscDirRadio().isSelected()) {
                    sortDir = "ASC";
                } else if (sortCriteriaPanel.getDescDirRadio().isSelected()){
                    sortDir = "DESC";
                }
                filters.put("sortDir", sortDir);
            }
        });
        add(applyFiltersButton, new GridBagConstraints(
                0, 7, 1, 1, 0, 0,
                GridBagConstraints.PAGE_START, GridBagConstraints.BOTH,
                new Insets(0, 0, 10, 0), 0, 0
        ));
    }

    public void setContentVisibility(boolean isVisible) {
        sortCriteriaPanel.setVisible(isVisible);
        filtersPanel.setVisible(isVisible);
        applyFiltersButton.setVisible(isVisible);
    }
}
