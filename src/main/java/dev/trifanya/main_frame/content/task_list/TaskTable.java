package dev.trifanya.main_frame.content.task_list;

import dev.trifanya.main_frame.MainFrame;

import javax.swing.*;

public class TaskTable extends JTable {
    public TaskTable(TaskTableModel taskTableModel) {
        super(taskTableModel);
        MainFrame.setBasicInterface(this);
        MainFrame.setBasicInterface(getTableHeader());
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
