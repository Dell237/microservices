package com.dely.ecommerce.payment;

import com.dely.ecommerce.notification.NotificationProducer;
import com.dely.ecommerce.notification.PaymentNotification;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository repository;
    private final PaymentMapper mapper;
    private final NotificationProducer notificationProducer;


    public Integer createPayment( PaymentReq paymentReq) {
        var payment = repository.save(mapper.toPayment(paymentReq));

        //send notification to notification server
         notificationProducer.sendPaymentNotification(
                new PaymentNotification(
                        paymentReq.orderReference(),
                        paymentReq.amount(),
                        paymentReq.paymentMethod(),
                        paymentReq.customer().firstname(),
                        paymentReq.customer().lastname(),
                        paymentReq.customer().email()
                )
        );


        return payment.getId();
    }
}
