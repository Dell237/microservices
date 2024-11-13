package com.dely.ecommerce.order;

import java.math.BigDecimal;

public record OrderRes(
        Integer id,
        String reference,
        PaymentMethod paymentMethod,
        String customerId
) {
}
