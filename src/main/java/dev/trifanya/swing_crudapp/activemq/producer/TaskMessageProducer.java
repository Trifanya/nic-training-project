package dev.trifanya.swing_crudapp.activemq.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.trifanya.swing_crudapp.SwingCRUDApp;
import dev.trifanya.swing_crudapp.model.Task;

import javax.jms.*;

import java.util.List;

import static javax.jms.Session.AUTO_ACKNOWLEDGE;

public class TaskMessageProducer {
    private Session session;
    private MessageProducer producer;
    private ObjectMapper objectMapper;

    public TaskMessageProducer() throws JMSException {
        session = SwingCRUDApp.connection.createSession(false, AUTO_ACKNOWLEDGE);
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    public void sendTask(Destination responseDestination, Task taskToSend) throws JMSException, JsonProcessingException {
        producer = session.createProducer(responseDestination);

        String messageToSend = objectMapper.writeValueAsString(taskToSend);
        TextMessage message = session.createTextMessage(messageToSend);
        message.setStringProperty("Response name", "Single task");

        producer.send(message);
    }

    public void sendTaskList(Destination responseDestination, List<Task> taskListToSend) throws JMSException, JsonProcessingException {
        for (Task task : taskListToSend) {
            System.out.println("id: " + task.getId() + " title: " + task.getTitle());
        }
        producer = session.createProducer(responseDestination);

        String messageToSend = objectMapper.writeValueAsString(taskListToSend);
        TextMessage message = session.createTextMessage(messageToSend);
        message.setStringProperty("Response name", "Task list");

        producer.send(message);
    }
}
