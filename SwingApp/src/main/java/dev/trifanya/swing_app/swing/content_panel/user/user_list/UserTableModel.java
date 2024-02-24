package dev.trifanya.swing_app.swing.content_panel.user.user_list;

import dev.trifanya.server_app.model.User;
import dev.trifanya.swing_app.swing.content_panel.ContentLayeredPane;

import java.util.List;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class UserTableModel extends AbstractTableModel {
    private final int columnCount = 5;
    private final List<String[]> tableData = new ArrayList<>();
    private List<User> userList = new ArrayList<>();

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

    public void fillTable() {
        tableData.clear();

        for (User user : userList) {
            String[] row = new String[columnCount];
            row[0] = Integer.toString(user.getId());
            row[1] = user.getName();
            row[2] = user.getSurname();
            row[3] = user.getPosition();
            row[4] = user.getEmail();
            tableData.add(row);
        }
    }

    public User getUserByEmail(String email) {
        for (User user : userList) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public void setUserList(List<User> userList, ContentLayeredPane contentLayeredPane) {
        this.userList = userList;
        fillTable();
        contentLayeredPane.getTaskFormPanel().updatePerformerBoxItems(userList);
    }
}
