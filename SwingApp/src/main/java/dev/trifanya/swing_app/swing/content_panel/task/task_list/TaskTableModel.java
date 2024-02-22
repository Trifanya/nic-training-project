package dev.trifanya.swing_app.swing.content_panel.task.task_list;

import dev.trifanya.server_app.model.Task;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TaskTableModel extends AbstractTableModel {
    private int columnCount = 5;
    private List<String[]> tableData;

    public TaskTableModel() {
        tableData = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return tableData.size();
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public String getValueAt(int rowIndex, int columnIndex) {
        return tableData.get(rowIndex)[columnIndex];
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch(columnIndex) {
            case 0: return "ID";
            case 1: return "TITLE";
            case 2: return "PRIORITY";
            case 3: return "DEADLINE";
            case 4: return "PERFORMER";
            default: throw new RuntimeException("Столбец с индексом " + columnIndex + " не найден.");
        }
    }

    public void fillTable(List<Task> tasks) {
        tableData.clear();

        for (Task task : tasks) {
            String[] row = new String[columnCount];
            row[0] = Integer.toString(task.getId());
            row[1] = task.getTitle();
            row[2] = task.getPriority().toString();
            row[3] = task.getDeadline() == null ? "" : task.getDeadline().toString();
            row[4] = task.getPerformer().getEmail();

            tableData.add(row);
        }
    }
}
