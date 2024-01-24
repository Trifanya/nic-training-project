package dev.trifanya.main_frame.content.task_list;

import dev.trifanya.dto.TaskDTO;
import dev.trifanya.server_connection.TaskClient;
import org.springframework.stereotype.Component;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TaskTableModel extends AbstractTableModel {
    private int columnCount = 5;

    private List<String[]> tableData = new ArrayList<>();

    private TaskClient taskClient;

    public TaskTableModel() {
        taskClient = new TaskClient();
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
            //case 2: return "STATUS";
            case 2: return "PRIORITY";
            case 3: return "DEADLINE";
            //case 5: return "AUTHOR";
            case 4: return "PERFORMER";
            default: throw new RuntimeException("Столбец с индексом " + columnIndex + " не найден.");
        }
    }

    public void fillTable() {
        tableData.clear();

        List<TaskDTO> tasks = taskClient.getAllTasks();

        for (TaskDTO task : tasks) {
            String[] row = new String[columnCount];
            row[0] = Integer.toString(task.getId());
            row[1] = task.getTitle();
            //row[2] = task.getStatus().toString();
            row[2] = task.getPriority().toString();
            //row[4] = task.getDeadline().toString();
            //row[5] = task.getAuthorEmail();
            row[4] = task.getPerformerEmail();

            tableData.add(row);
        }
    }


}
