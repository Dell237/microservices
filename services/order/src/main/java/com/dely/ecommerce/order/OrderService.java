package com.dely.ecommerce.order;

import com.dely.ecommerce.customer.CustomerClient;
import com.dely.ecommerce.exception.BusinessException;
import com.dely.ecommerce.kafka.OrderConfirmation;
import com.dely.ecommerce.kafka.OrderProducer;
import com.dely.ecommerce.orderline.OrderLineRequest;
import com.dely.ecommerce.orderline.OrderLineService;
import com.dely.ecommerce.payment.PaymentClient;
import com.dely.ecommerce.payment.PaymentReq;
import com.dely.ecommerce.product.ProductClient;
import com.dely.ecommerce.product.PurchaseRequest;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {


    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final  OrderRepository repository;
    private final  OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    @Transactional
    public Integer createOrder( OrderRequest request) {
        //check the customer    --> OpenFeign
        var customer  = customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("customer with id not found " + request.customerId()));

        System.out.println("customer: " + customer);
        //purchase the products --> product_ms
       var purchasedProducts=  productClient.purchaseProducts(request.products());

        //persist order
        var order = repository.save(mapper.toOrder(request));

        //persist order lines
        for (PurchaseRequest purchaseRequest : request.products()){
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );

        }
        // start payment process
        var paymentReq = new PaymentReq(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentReq);


        //send the order confirmation --> notifications_ms (kafka)
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );


        return order.getId();
    }

    public List<OrderRes> findAll() {
        return  repository.findAll()
                .stream()
                .map(mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderRes findById(Integer orderId) {
        return repository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException("no order found with id: "+ orderId));
    }
}
