package com.example.lab05;

import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

import java.util.List;

public class Receiver {
    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();

        factory.setTrustedPackages(List.of("com.example.lab05"));

        Connection connection = factory.createConnection();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        connection.start();

        Destination destination = session.createQueue("sonnees");
        MessageConsumer consumer = session.createConsumer(destination);

        factory.setTrustedPackages(List.of("com.example.mq.entity"));
        System.out.println("Waiting....");

        consumer.setMessageListener(message -> {
            try {
                if (message instanceof TextMessage) {
                    String mess = ((TextMessage) message).getText();
                    System.out.println("*** "+mess);
                } else if (message instanceof ObjectMessage) {
                    Student student = message.getBody(Student.class);
                    System.out.println("*** "+ student);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
