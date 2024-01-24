package dev.trifanya;

import dev.trifanya.main_frame.MainFrame;
import dev.trifanya.main_frame.content.ContentLayeredPane;
import dev.trifanya.main_frame.menu.MenuPanel;
import dev.trifanya.main_frame.sort_and_filters.FiltersPanel;
import dev.trifanya.main_frame.sort_and_filters.SortCriteriaPanel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.server.WebServer;
import org.springframework.context.annotation.Bean;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.init();
    }
}