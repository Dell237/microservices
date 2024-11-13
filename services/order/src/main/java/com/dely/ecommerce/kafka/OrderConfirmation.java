package com.dely.ecommerce.kafka;

import com.dely.ecommerce.customer.CustomerResponse;
import com.dely.ecommerce.order.PaymentMethod;
import com.dely.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
