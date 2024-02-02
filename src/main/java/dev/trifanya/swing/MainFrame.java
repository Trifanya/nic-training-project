package dev.trifanya.swing;

import dev.trifanya.swing.content.ContentLayeredPane;
import dev.trifanya.swing.menu.MenuPanel;
import dev.trifanya.swing.sort_and_filters.SortAndFiltersPanel;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

@Getter
@Setter
public class MainFrame extends JFrame {
    public static int frameWidth = 1400;
    public static int frameHeight = 800;
    public static int frameMinWidth = 1000;
    public static int frameMinHeight = 600;
    public static Color firstColor = Color.WHITE;
    public static Color secondColor = Color.BLACK;
    public static Font font = new Font(Font.SANS_SERIF, Font.BOLD, 16);
    public static Border border = new LineBorder(MainFrame.secondColor, 3, true);

    private MenuPanel menuPanel;
    private ContentLayeredPane contentLayeredPane;
    private SortAndFiltersPanel sortAndFiltersPanel;


    public MainFrame() {
        setLocation(350, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(frameWidth, frameHeight));
        setMinimumSize(new Dimension(frameMinWidth, frameMinHeight));
        getContentPane().setBackground(firstColor);
    }

    public void init() {
        menuPanel = new MenuPanel(this);
        contentLayeredPane = new ContentLayeredPane(this);
        sortAndFiltersPanel = new SortAndFiltersPanel(this);

        menuPanel.init(contentLayeredPane);
        add(menuPanel, BorderLayout.WEST);

        contentLayeredPane.init();
        add(contentLayeredPane, BorderLayout.CENTER);

        sortAndFiltersPanel.init(contentLayeredPane);
        add(sortAndFiltersPanel, BorderLayout.EAST);

        setVisible(true);
    }

    public static void setBasicInterface(JComponent component) {
        component.setBackground(firstColor);
        component.setForeground(secondColor);
        component.setFont(font);
        component.setBorder(border);
    }
}
