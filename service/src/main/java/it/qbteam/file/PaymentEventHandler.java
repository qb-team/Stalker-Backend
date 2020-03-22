package it.qbteam.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventHandler {

    @Autowired
    private RedisMessagePublisher publisher;

    public void handlePaymentSave( Moviment moviment) {
        publisher.publish(moviment);
    }
}