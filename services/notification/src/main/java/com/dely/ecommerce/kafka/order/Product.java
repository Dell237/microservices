package com.dely.ecommerce.kafka.order;

import java.math.BigDecimal;

public record Product(
         Integer productId,
         String name,
         String description,
         double quantity,
         BigDecimal price
) {
}
