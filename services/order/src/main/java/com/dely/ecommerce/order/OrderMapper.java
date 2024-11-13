package com.dely.ecommerce.order;

import org.springframework.stereotype.Service;

@Service
public class OrderMapper {


    public Order toOrder(OrderRequest request) {
        return Order.builder()
                .id(request.id())
                .reference(request.reference())
                .totalAmount(request.amount())
                .customerId(request.customerId())
                .paymentMethod(request.paymentMethod())
                .build();
    }

    public OrderRes fromOrder(Order order) {
        return  new OrderRes(
                order.getId(),
                order.getReference(),
                order.getPaymentMethod(),
                order.getCustomerId()
        );
    }
}
