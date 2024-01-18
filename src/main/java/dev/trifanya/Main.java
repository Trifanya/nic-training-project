package dev.trifanya;

import dev.trifanya.component.*;
import dev.trifanya.server_connection.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

@RequiredArgsConstructor
public class Main {
    public static final Font font = new Font(Font.SANS_SERIF, Font.BOLD, 16);
    public static int frameWidth = 1400;
    public static int frameHeight = 800;
    public static int frameMinWidth = 1000;
    public static int frameMinHeight = 600;
    public static Color first = Color.BLACK;
    public static Color secondColor = Color.ORANGE;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Task Management Service");
        frame.setLocation(350, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(frameWidth, frameHeight));
        frame.setMinimumSize(new Dimension(frameMinWidth, frameMinHeight));
        Container contentPane = frame.getContentPane();
        contentPane.setBackground(first);

        /**
         * ПАНЕЛЬ С МЕНЮ
         */
        MenuPanel menuPanel = new MenuPanel();
        frame.add(menuPanel, BorderLayout.WEST);


        /**
         * ПАНЕЛЬ С ОСНОВНЫМ СОДЕРЖИМЫМ
         */
        ContentLayeredPane contentLayeredPane = new ContentLayeredPane();
        contentLayeredPane.init();
        frame.add(contentLayeredPane, BorderLayout.CENTER);

        menuPanel.setContentPane(contentLayeredPane);

        /**
         * ПАНЕЛЬ КРИТЕРИЕВ СОРТИРОВКИ И ФИЛЬТРАЦИИ ПОИСКА
         */
        JPanel sortAndFiltersPanel = new JPanel();
        sortAndFiltersPanel.setLayout(new GridBagLayout());
        sortAndFiltersPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sortAndFiltersPanel.setBackground(first);
        sortAndFiltersPanel.setBorder(new LineBorder(secondColor, 3, true));
        sortAndFiltersPanel.setPreferredSize(new Dimension(250, 0));
        frame.add(sortAndFiltersPanel, BorderLayout.EAST);

        /**
         * ПАНЕЛЬ КРИТЕРИЕВ СОРТИРОВКИ
         */
        SortCriteriaPanel sortCriteriaPanel = new SortCriteriaPanel();
        sortAndFiltersPanel.add(sortCriteriaPanel, new GridBagConstraints(
                0, 0, 1, 1, 1, 0,
                GridBagConstraints.PAGE_START, GridBagConstraints.BOTH,
                new Insets(15, 20, 15, 20), 0, 0));

        /**
         * ПАНЕЛЬ ФИЛЬТРОВ ПОИСКА
         */
        FiltersPanel filtersPanel = new FiltersPanel();
        sortAndFiltersPanel.add(filtersPanel, new GridBagConstraints(
                0, 1, 1, 2, 1, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 20, 15, 20), 0, 0));



        frame.setVisible(true);
    }
}