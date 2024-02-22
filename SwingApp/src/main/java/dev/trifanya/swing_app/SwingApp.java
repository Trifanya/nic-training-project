package dev.trifanya.swing_app;

import dev.trifanya.swing_app.activemq.listener.ServerListener;
import dev.trifanya.swing_app.config.PropertiesLoader;
import dev.trifanya.swing_app.swing.MainFrame;
import lombok.experimental.Accessors;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Properties;

import static javax.jms.Session.AUTO_ACKNOWLEDGE;
import static org.apache.activemq.ActiveMQConnection.DEFAULT_BROKER_URL;

@Accessors(chain = true)
public class SwingApp {
    public static final Properties properties = PropertiesLoader.loadProperties("application.properties");

    public static final String CLIENT_ID = properties.getProperty("activemqClientId");
    public static final String REQUEST_FROM_SWING_CLIENT_QUEUE = properties.getProperty("swingRequestQueueName");

    public static ConnectionFactory connectionFactory;
    public static Connection connection;

    public static void main(String[] args) throws JMSException {
        MainFrame mainFrame = new MainFrame();

        System.out.println("Устанавливается соединение с сервером ActiveMQ...");

        connectionFactory = new ActiveMQConnectionFactory(DEFAULT_BROKER_URL);
        connection = connectionFactory.createConnection();
        connection.setClientID(CLIENT_ID);
        connection.start();

        Session session = connection.createSession(false, AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(REQUEST_FROM_SWING_CLIENT_QUEUE);
        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(new ServerListener(mainFrame));

        mainFrame.init();
    }
}
