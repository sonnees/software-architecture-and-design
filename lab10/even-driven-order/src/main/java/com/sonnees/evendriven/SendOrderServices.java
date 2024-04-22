package com.sonnees.evendriven;

import com.sonnees.OrderResponse;
import jakarta.jms.JMSException;
import jakarta.jms.Session;
import lombok.AllArgsConstructor;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.core.annotation.Order;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import jakarta.jms.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import jakarta.jms.Destination;

@AllArgsConstructor
@Component
public class SendOrderServices  {
    private JmsTemplate template;
    @SendTo("inventory.topic_01")
    public String sendOrder(String jsonDocs){
        Destination destination = new ActiveMQTopic("inventory.topic_01");
        MessageCreator msg= new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(jsonDocs);
            }
        };
        template.send(destination,msg);

        return "SUCCESS";
    }
}

