package dev.trifanya.swing_crudapp.activemq.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.trifanya.swing_crudapp.SwingCRUDApp;
import dev.trifanya.swing_crudapp.model.User;

import javax.jms.*;
import java.util.List;

import static javax.jms.Session.AUTO_ACKNOWLEDGE;

public class UserMessageProducer {
    private Session session;
    private MessageProducer producer;
    private ObjectMapper objectMapper;
    private final String RESPONSE_CORRELATION_ID = "rspCrlID";

    public UserMessageProducer() throws JMSException {
        session = SwingCRUDApp.connection.createSession(false, AUTO_ACKNOWLEDGE);
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    public void sendUser(Destination responseDestination, User userToSend) throws JMSException, JsonProcessingException {
        producer = session.createProducer(responseDestination);

        String messageToSend = objectMapper.writeValueAsString(userToSend);
        TextMessage message = session.createTextMessage(messageToSend);
        message.setStringProperty("Response name", "Single user");
        message.setJMSCorrelationID(RESPONSE_CORRELATION_ID);
        producer.send(message);
    }

    public void sendUserList(Destination responseDestination, List<User> userListToSend) throws JMSException {
        System.out.println("Found:");
        for (User user : userListToSend) {
            System.out.println(user.getName() + " " + user.getSurname());
        }
        producer = session.createProducer(responseDestination);
        String messageToSend = null;
        try {
            messageToSend = objectMapper.writeValueAsString(userListToSend);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        TextMessage message = session.createTextMessage(messageToSend);
        message.setStringProperty("Response name", "User list");

        producer.send(message);
    }
}
