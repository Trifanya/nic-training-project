package dev.trifanya.server_app;

import dev.trifanya.server_app.activemq.listener.ClientListener;
import dev.trifanya.server_app.config.PropertiesLoader;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jms.*;
import java.util.Properties;

import static javax.jms.Session.AUTO_ACKNOWLEDGE;
import static org.apache.activemq.ActiveMQConnection.DEFAULT_BROKER_URL;

public class ServerApp {
    public static final Logger logger = LogManager.getLogger();
    public static final Properties properties = PropertiesLoader.loadProperties("application.properties");

    public static final String CLIENT_ID = properties.getProperty("activemqClientId");
    public static final String REQUEST_FROM_WEB_CLIENT_QUEUE = properties.getProperty("webRequestQueueName");
    public static final String REQUEST_FROM_SWING_CLIENT_QUEUE = properties.getProperty("swingRequestQueueName");
    public static final String RESPONSE_FROM_SERVER_QUEUE = properties.getProperty("serverResponseQueueName");

    public static ConnectionFactory connectionFactory;
    public static Connection connection;

    public static void main(String[] args) throws JMSException {
        logger.info("Устанавливается соединение с сервером ActiveMQ...");

        connectionFactory = new ActiveMQConnectionFactory(DEFAULT_BROKER_URL);
        connection = connectionFactory.createConnection();
        connection.setClientID(CLIENT_ID);
        connection.start();

        logger.info("Соединение с сервером ActiveMQ установлено.");

        Session session = ServerApp.connection.createSession(false, AUTO_ACKNOWLEDGE);

        Destination destinationWeb = session.createQueue(REQUEST_FROM_WEB_CLIENT_QUEUE);
        MessageConsumer webClientConsumer = session.createConsumer(destinationWeb);
        webClientConsumer.setMessageListener(new ClientListener());

        Destination destinationFromSwing = session.createQueue(REQUEST_FROM_SWING_CLIENT_QUEUE);
        Destination destinationToSwing = session.createQueue(RESPONSE_FROM_SERVER_QUEUE);
        MessageConsumer swingClientConsumer = session.createConsumer(destinationFromSwing);
        swingClientConsumer.setMessageListener(new ClientListener(destinationToSwing));
    }
}