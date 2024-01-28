package dev.trifanya.activemq;

import dev.trifanya.SwingCRUDApp;

import javax.jms.*;

import static javax.jms.Session.AUTO_ACKNOWLEDGE;

public class MainMessageConsumer {
    private Session session;
    private Destination destination;
    private MessageConsumer consumer;

    public MainMessageConsumer() throws JMSException {
        session = SwingCRUDApp.connection.createSession(false, AUTO_ACKNOWLEDGE);

        destination = session.createQueue(SwingCRUDApp.requestFromClientQueue);
        consumer = session.createConsumer(destination);
        consumer.setMessageListener(new CustomMessageListener());
    }
}
