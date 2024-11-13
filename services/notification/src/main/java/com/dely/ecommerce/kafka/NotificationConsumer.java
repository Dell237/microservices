package com.dely.ecommerce.kafka;

import com.dely.ecommerce.email.EmailService;
import com.dely.ecommerce.kafka.order.OrderConfirmation;
import com.dely.ecommerce.kafka.payment.PaymentConfirmation;
import com.dely.ecommerce.notification.Notification;
import com.dely.ecommerce.notification.NotificationRepository;
import com.dely.ecommerce.notification.NotificationType;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private  final NotificationRepository repository;
    private final EmailService emailService;

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotification(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info("Consuming the msg from payment-topic Topic: ");

        repository.save(
                Notification.builder()
                        .type(NotificationType.PAYMENT_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .paymentConfirmation(paymentConfirmation)
                        .build()
        );

        // send email..
        var customerName = paymentConfirmation.customerFirstname() + " " + paymentConfirmation.customerLastname();
        emailService.sentPaymentSuccessEmail(
//                paymentConfirmation.customerEmail(),
                "dely@gmail.com",
                customerName,
                paymentConfirmation.amount(),
                paymentConfirmation.orderReference()
        );

    }


    @KafkaListener(topics = "order-topic")
    public void consumeOrderSuccessNotification(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info("Consuming the msg from order-topic Topic :: %s");
        log.info("orderConfirmation: {}", orderConfirmation);
        repository.save(
                Notification.builder()
                        .type(NotificationType.ORDER_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .orderConfirmation(orderConfirmation)
                        .build()
        );

        // send email...
        var customerName = orderConfirmation.customer().firstname() + " " + orderConfirmation.customer().lastname();

        emailService.sentOrderSuccessEmail(
                //orderConfirmation.customer().email(),
                "dely@gmail.com",
                customerName,
                orderConfirmation.totalAmount(),
                orderConfirmation.orderReference(),
                orderConfirmation.products()
               );

    }


}
