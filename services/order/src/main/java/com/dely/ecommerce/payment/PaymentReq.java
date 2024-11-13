package com.dely.ecommerce.payment;

import com.dely.ecommerce.customer.CustomerResponse;
import com.dely.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentReq(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
