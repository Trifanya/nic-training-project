package dev.trifanya.swing.content.task_list;

import dev.trifanya.model.Task;
import dev.trifanya.service.TaskFiltersBuilder;
import dev.trifanya.service.TaskService;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaskTableModel extends AbstractTableModel {
    private int columnCount = 5;
    private List<String[]> tableData;
    private TaskService taskService;

    public TaskTableModel() {
        tableData = new ArrayList<>();
        taskService = new TaskService();
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

    public void fillTable(Map<String, String> filters) {
        tableData.clear();

        List<Task> tasks;
        if (filters == null || filters.isEmpty()) tasks = taskService.getAllTasks();
        else tasks = taskService.getFilteredTasks(filters);

        for (Task task : tasks) {
            String[] row = new String[columnCount];
            row[0] = Integer.toString(task.getId());
            row[1] = task.getTitle();
            row[2] = task.getPriority().toString();
            row[4] = task.getPerformer().getEmail();

            tableData.add(row);
        }
    }


}
