package dev.trifanya.swing_crudapp;

import dev.trifanya.swing_crudapp.activemq.consumer.RequestMessageConsumer;
import dev.trifanya.swing_crudapp.swing.MainFrame;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import static org.apache.activemq.ActiveMQConnection.DEFAULT_BROKER_URL;

public class SwingCRUDApp {
    public static final String CLIENTID = "SwingCRUDApp";
    public static final String requestFromClientQueue = "Request Queue";

    public static ConnectionFactory connectionFactory;
    public static Connection connection;

    public static void main(String[] args) throws JMSException {
            MainFrame mainFrame = new MainFrame();

            System.out.println(DEFAULT_BROKER_URL);

            System.out.println("fasjlfs;dk");
            connectionFactory = new ActiveMQConnectionFactory(DEFAULT_BROKER_URL);
            connection = connectionFactory.createConnection();
            connection.setClientID(CLIENTID);
            connection.start();

            RequestMessageConsumer requestMessageConsumer = new RequestMessageConsumer();
            mainFrame.init();

    }
}