package com.dely.ecommerce.customer;

import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {

    public Customer toCustomer(CustomerRequest request) {
        if (request == null) {
            return null;
        }
        return Customer.builder()
                .id(request.id())
                .firstname(request.firstname())
                .lastname(request.lastname())
                .address(request.address())
                .email(request.email())
                .build();
    }

    public CustomerResponse fromCustomer(Customer c) {
        return new CustomerResponse(
                c.getId(),
                c.getFirstname(),
                c.getLastname(),
                c.getEmail(),
                c.getAddress()
        );
    }

}
