package dev.trifanya.swing_app.swing.content_panel.task.task_list;

import dev.trifanya.server_app.model.Task;
import dev.trifanya.swing_app.swing.content_panel.ContentLayeredPane;

import java.util.List;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class TaskTableModel extends AbstractTableModel {
    private final int columnCount = 5;
    private final List<String[]> tableData = new ArrayList<>();
    private List<Task> taskList = new ArrayList<>();

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
        switch (columnIndex) {
            case 0:
                return "ID";
            case 1:
                return "TITLE";
            case 2:
                return "PRIORITY";
            case 3:
                return "DEADLINE";
            case 4:
                return "PERFORMER";
            default:
                throw new RuntimeException("Столбец с индексом " + columnIndex + " не найден.");
        }
    }

    public void fillTable() {
        tableData.clear();

        for (Task task : taskList) {
            String[] row = new String[columnCount];
            row[0] = Integer.toString(task.getId());
            row[1] = task.getTitle();
            row[2] = task.getPriority().toString();
            row[3] = task.getDeadline() == null ? "" : task.getDeadline().toString();
            row[4] = task.getPerformer().getEmail();
            tableData.add(row);
        }
    }

    public Task getTaskById(int taskId) {
        for (Task task : taskList) {
            if (task.getId() == taskId) {
                return task;
            }
        }
        return null;
    }

    public void setTaskList(List<Task> taskList, ContentLayeredPane contentLayeredPane) {
        this.taskList = taskList;
        fillTable();
        contentLayeredPane.getTaskListPanel().repaint();
    }
}
