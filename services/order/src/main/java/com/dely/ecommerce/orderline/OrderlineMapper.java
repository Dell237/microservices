package com.dely.ecommerce.orderline;

import com.dely.ecommerce.order.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderlineMapper {

    public OrderLine toOrderLine(OrderLineRequest orderLineRequest) {
        return  OrderLine.builder()
                .id(orderLineRequest.id())
                .order(
                        Order.builder()
                                .id(orderLineRequest.orderId())
                                .build()
                )
                .quantity(orderLineRequest.quantity())
                .productId(orderLineRequest.productId())
                .build();
    }

    public OrderLineRes toOrderLineRes(OrderLine orderLine) {
        return new OrderLineRes(
                orderLine.getId(),
                orderLine.getQuantity()

        );
    }
}
