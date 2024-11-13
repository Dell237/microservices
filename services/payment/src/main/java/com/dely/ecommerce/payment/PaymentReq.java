package com.dely.ecommerce.payment;

import java.math.BigDecimal;

public record PaymentReq(
        Integer id,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        Customer customer
) {
}
