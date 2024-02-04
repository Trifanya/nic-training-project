package dev.trifanya.swing.content_panel.user.user_list;

import dev.trifanya.model.User;
import dev.trifanya.service.UserService;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class UserTableModel extends AbstractTableModel {
    private int columnCount = 5;
    private List<String[]> tableData;
    private UserService userService;

    public UserTableModel() {
        tableData = new ArrayList<>();
        userService = new UserService();
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
            case 1: return "NAME";
            case 2: return "SURNAME";
            case 3: return "POSITION";
            case 4: return "EMAIL";
            default: throw new RuntimeException("Столбец с индексом " + columnIndex + " не найден.");
        }
    }

    public void fillTable(String sortByColumn, String sortDir) {
        tableData.clear();

        List<User> users = userService.getUsers(sortByColumn, sortDir);

        for (User user : users) {
            String[] row = new String[columnCount];
            row[0] = Integer.toString(user.getId());
            row[1] = user.getName();
            row[2] = user.getSurname();
            row[3] = user.getPosition();
            row[4] = user.getEmail();

            tableData.add(row);
        }
    }
}
