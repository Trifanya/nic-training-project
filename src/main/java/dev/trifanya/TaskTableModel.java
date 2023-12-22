package dev.trifanya;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TaskTableModel extends AbstractTableModel {
    private int columnCount = 6;

    private List<String[]> tableData = new ArrayList<>();

    public TaskTableModel() {
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

    public void addTask() {

    }
}
