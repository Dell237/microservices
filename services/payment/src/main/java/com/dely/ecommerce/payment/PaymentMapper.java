package com.dely.ecommerce.payment;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class PaymentMapper {

    public Payment toPayment(PaymentReq req) {
        return Payment.builder()
                .id(req.id())
                .paymentMethod(req.paymentMethod())
                .amount(req.amount())
                .orderId(req.orderId())
                .createdDate(LocalDateTime.now())
                .build();
    }
}
