package dev.trifanya.swing_crudapp;

import dev.trifanya.swing_crudapp.activemq.consumer.RequestMessageConsumer;
import dev.trifanya.swing_crudapp.config.PropertiesLoader;
import dev.trifanya.swing_crudapp.swing.MainFrame;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import java.util.Properties;

import static org.apache.activemq.ActiveMQConnection.DEFAULT_BROKER_URL;

public class SwingCRUDApp {
    public static final Properties properties = PropertiesLoader.loadProperties("application.properties");
    public static final String CLIENT_ID = properties.getProperty("activemqClientId");
    public static final String REQUEST_FROM_CLIENT_QUEUE = properties.getProperty("requestQueueName");

    public static ConnectionFactory connectionFactory;
    public static Connection connection;

    public static void main(String[] args) throws JMSException {
        ProcessBuilder processBuilder = new ProcessBuilder(
                "/deb/bin/nictp_crudapp.sh",
                properties.getProperty("astraAppdir")
        );
        MainFrame mainFrame = new MainFrame();

        System.out.println(DEFAULT_BROKER_URL);

        connectionFactory = new ActiveMQConnectionFactory(DEFAULT_BROKER_URL);
        connection = connectionFactory.createConnection();
        connection.setClientID(CLIENT_ID);
        connection.start();

        RequestMessageConsumer requestMessageConsumer = new RequestMessageConsumer();
        mainFrame.init();
    }
}