package dev.trifanya.main_frame.content.task_list;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class Renderer extends DefaultTableCellRenderer {
    private Color firstColor = Color.BLACK;
    private Color secondColor = Color.ORANGE;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (isSelected) {
            setBackground(secondColor);
            setForeground(firstColor);
        } else {
            setBackground(firstColor);
            setForeground(secondColor);
        }

        return cell;
    }
}
