package dev.trifanya.component;

import dev.trifanya.dto.TaskDTO;
import dev.trifanya.server_connection.TaskClient;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TaskTableModel extends AbstractTableModel {
    private int columnCount = 6;

    private List<String[]> tableData = new ArrayList<>();

    private TaskClient taskClient;

    public TaskTableModel() {
        this.taskClient = new TaskClient();
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
    public Object getValueAt(int rowIndex, int columnIndex) {
        return tableData.get(rowIndex)[columnIndex];
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch(columnIndex) {
            case 0: return "title";
            case 1: return "status";
            case 2: return "priority";
            case 3: return "deadline";
            case 4: return "author";
            case 5: return "performer";
            default: throw new RuntimeException("Столбец с индексом " + columnIndex + " не найден.");
        }
    }

    public void fillTable() {
        tableData.clear();

        List<TaskDTO> tasks = taskClient.getAllTasks();

        for (TaskDTO task : tasks) {
            String[] row = new String[columnCount];
            row[0] = task.getTitle();
            row[1] = task.getStatus().toString();
            row[2] = task.getPriority().toString();
            //row[3] = task.getDeadline().toString();
            row[4] = task.getAuthorEmail();
            row[5] = task.getPerformerEmail();

            tableData.add(row);
        }
    }
}
