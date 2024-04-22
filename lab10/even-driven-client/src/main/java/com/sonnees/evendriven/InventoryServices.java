package com.sonnees.evendriven;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class InventoryServices {
    @JmsListener(destination = "inventory.topic_01")
    public void receiveMessage(Message<String> message){
        String payload = message.getPayload();
        System.out.println("*** received message: \n"+payload);
    }
}

