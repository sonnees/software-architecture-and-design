package vn.edu.iuh.fit.activemq;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class OrderProductListener {
    @JmsListener(destination = "order_product")
    public void receiveMessage(final Message jsonMessage) throws JMSException {
        String messageData = null;
        String response = null;
        if(jsonMessage instanceof TextMessage) {

            //1. read message data
            //2. ==> decode
            //3. check for quantity
            //4. make order or reject
            //5. send email
        }
    }
}
