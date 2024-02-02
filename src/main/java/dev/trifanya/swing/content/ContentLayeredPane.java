package dev.trifanya.swing.content;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import dev.trifanya.swing.MainFrame;
import dev.trifanya.swing.content.task.task_form.TaskFormPanel;
import dev.trifanya.swing.content.task.task_list.TaskListPanel;
import dev.trifanya.swing.content.task.task_page.TaskDetailsPanel;
import dev.trifanya.swing.content.user.user_form.UserFormPanel;
import dev.trifanya.swing.content.user.user_list.UserListPanel;
import dev.trifanya.swing.content.user.user_page.UserDetailsPanel;
import dev.trifanya.swing.menu.MenuPanel;
import lombok.Getter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

@Getter
public class ContentLayeredPane extends JLayeredPane {
    private MainFrame mainFrame;

    private TaskListPanel taskListPanel;
    private TaskFormPanel taskFormPanel;
    private TaskDetailsPanel taskDetailsPanel;

    private UserListPanel userListPanel;
    private UserFormPanel userFormPanel;
    private UserDetailsPanel userDetailsPanel;

    public ContentLayeredPane(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridBagLayout());
        setBackground(MainFrame.firstColor);
        setBorder(new LineBorder(MainFrame.secondColor, 3, true));
        setMinimumSize(new Dimension(600, 400));
    }

    public void init() {
        taskListPanel = new TaskListPanel();
        taskListPanel.init(this);
        add(taskListPanel, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        setLayer(taskListPanel, new Integer(0));

        taskFormPanel = new TaskFormPanel();
        taskFormPanel.init(this);
        add(taskFormPanel, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
        setLayer(taskFormPanel, new Integer(1));

        taskDetailsPanel = new TaskDetailsPanel();
        taskDetailsPanel.init();
        add(taskDetailsPanel, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
        setLayer(taskDetailsPanel, new Integer(0));

        /**
         * USER
         */
        userListPanel = new UserListPanel();
        userListPanel.init(this);
        add(userListPanel, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        setLayer(userListPanel, new Integer(0));

        userFormPanel = new UserFormPanel();
        userFormPanel.init(this);
        add(userFormPanel, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        setLayer(userFormPanel, new Integer(1));

        userDetailsPanel = new UserDetailsPanel();
        userDetailsPanel.init();
        add(userDetailsPanel, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        setLayer(userDetailsPanel, new Integer(0));
    }

    public void putPanelOnTop(String contentPanel) {
        switch (contentPanel) {
            case "NEW TASK":
                System.out.println("Сработал первый кейс");
                setLayer(taskFormPanel, new Integer(1));
                setLayer(taskListPanel, new Integer(0));
                setLayer(taskDetailsPanel, new Integer(0));
                setLayer(userFormPanel, new Integer(0));
                setLayer(userListPanel, new Integer(0));
                setLayer(userDetailsPanel, new Integer(0));
                break;
            case "TASK LIST":
                System.out.println("Сработал второй кейс");
                setLayer(taskFormPanel, new Integer(0));
                setLayer(taskListPanel, new Integer(1));
                setLayer(taskDetailsPanel, new Integer(0));
                setLayer(userFormPanel, new Integer(0));
                setLayer(userListPanel, new Integer(0));
                setLayer(userDetailsPanel, new Integer(0));
                break;
            case "TASK DETAILS":
                System.out.println("Сработал третий кейс");
                setLayer(taskFormPanel, new Integer(0));
                setLayer(taskListPanel, new Integer(0));
                setLayer(taskDetailsPanel, new Integer(1));
                setLayer(userFormPanel, new Integer(0));
                setLayer(userListPanel, new Integer(0));
                setLayer(userDetailsPanel, new Integer(0));
                break;
            case "NEW USER":
                System.out.println("Сработал четвертый кейс");
                setLayer(taskFormPanel, new Integer(0));
                setLayer(taskListPanel, new Integer(0));
                setLayer(taskDetailsPanel, new Integer(0));
                setLayer(userFormPanel, new Integer(1));
                setLayer(userListPanel, new Integer(0));
                setLayer(userDetailsPanel, new Integer(0));
                break;
            case "USER LIST":
                System.out.println("Сработал пятый кейс");
                setLayer(taskFormPanel, new Integer(0));
                setLayer(taskListPanel, new Integer(0));
                setLayer(taskDetailsPanel, new Integer(0));
                setLayer(userFormPanel, new Integer(0));
                setLayer(userListPanel, new Integer(1));
                setLayer(userDetailsPanel, new Integer(0));
                break;
            case "USER DETAILS":
                System.out.println("Сработал шестой кейс");
                setLayer(taskFormPanel, new Integer(0));
                setLayer(taskListPanel, new Integer(0));
                setLayer(taskDetailsPanel, new Integer(0));
                setLayer(userFormPanel, new Integer(0));
                setLayer(userListPanel, new Integer(0));
                setLayer(userDetailsPanel, new Integer(1));
                break;
        }
    }
}
