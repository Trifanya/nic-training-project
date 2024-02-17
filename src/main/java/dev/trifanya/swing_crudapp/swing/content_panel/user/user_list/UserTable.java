package dev.trifanya.swing_crudapp.swing.content_panel.user.user_list;

import dev.trifanya.swing_crudapp.swing.MainFrame;

import javax.swing.*;
import javax.swing.table.JTableHeader;

public class UserTable extends JTable {
    public UserTable(UserTableModel userTableModel) {
        super(userTableModel);
        setBackground(MainFrame.firstColor);
        setForeground(MainFrame.secondColor);
        setFont(MainFrame.font);
        JTableHeader header = getTableHeader();
        header.setBackground(MainFrame.firstColor);
        header.setForeground(MainFrame.secondColor);
        header.setFont(MainFrame.font);
        /*getTableHeader().setBackground(MainFrame.firstColor);
        getTableHeader().setForeground(MainFrame.secondColor);
        getTableHeader().setFont(MainFrame.font);*/
        setGridColor(MainFrame.secondColor);
        setSelectionBackground(MainFrame.secondColor);
        setSelectionForeground(MainFrame.firstColor);
        setFillsViewportHeight(true);
        /*for (int i = 0; i < taskTable.getColumnCount(); i++) {
            taskTable.getColumnModel().getColumn(i).setCellRenderer(new Renderer());
        }*/
    }
}
