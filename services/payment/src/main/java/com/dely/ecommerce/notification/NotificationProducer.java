package com.dely.ecommerce.notification;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationProducer {
    private final KafkaTemplate<String, PaymentNotification> kafkaTemplate;

    public void sendPaymentNotification(PaymentNotification req){
        log.info("sending payment notification");
        // Sicherstellen, dass die Werte korrekt verwendet werden
        System.out.println("Customer Firstname: " + req.customerFirstname());
        System.out.println("Customer Email: " + req.customerEmail());
        Message<PaymentNotification> message = MessageBuilder
                .withPayload(req)
                .setHeader(KafkaHeaders.TOPIC, "payment-topic")
                .build();
        kafkaTemplate.send(message);

    }
}
