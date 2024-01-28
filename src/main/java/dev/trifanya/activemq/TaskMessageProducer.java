package dev.trifanya.activemq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.trifanya.SwingCRUDApp;
import dev.trifanya.model.Task;

import javax.jms.*;

import java.util.List;

import static javax.jms.Session.AUTO_ACKNOWLEDGE;

public class TaskMessageProducer {
    private Session session;
    private Destination destination;
    private MessageProducer producer;
    private ObjectMapper objectMapper;

    public TaskMessageProducer() throws JMSException {
        session = SwingCRUDApp.connection.createSession(false, AUTO_ACKNOWLEDGE);
        destination = session.createQueue(SwingCRUDApp.responseFromServerQueue);
        producer = session.createProducer(destination);
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }


    public void sendTask(Task taskToSend) throws JMSException {
        String messageToSend = null;
        try {
            messageToSend = objectMapper.writeValueAsString(taskToSend);
        } catch(JsonProcessingException e) {
            e.printStackTrace();
        }
        TextMessage message = session.createTextMessage(messageToSend);
        message.setStringProperty("Response name", "Single task");

        producer.send(message);
        System.out.println("Task sent");
    }

    public void sendTaskList(List<Task> taskListToSend) throws JMSException{
        String messageToSend = null;
        try {
            messageToSend = objectMapper.writeValueAsString(taskListToSend);
        } catch(JsonProcessingException e) {
            e.printStackTrace();
        }
        TextMessage message = session.createTextMessage(messageToSend);
        message.setStringProperty("Response name", "Task list");

        producer.send(message);

        System.out.println("List sent");
    }
}
