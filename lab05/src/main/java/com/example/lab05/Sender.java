package com.example.lab05;

import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

import java.util.List;

public class Sender {
    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        Connection connection = factory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        connection.start();

        Destination destination = session.createQueue("sonnees");
        MessageProducer producer = session.createProducer(destination);

        System.out.println("Waiting....");

        TextMessage textMessage = session.createTextMessage("message text oke");
        producer.send(textMessage);

        Student st = new Student("Nguyen Van Son");
        ObjectMessage objectMessage = session.createObjectMessage(st);
        producer.send(objectMessage);

        System.out.println("sending....");
    }
}
