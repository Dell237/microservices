package com.dely.ecommerce.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerResponse(
        String id,
        @NotNull(message = "customer firstname is required")
        String firstname,
        @NotNull(message = "customer lastname is required")
        String lastname,
        @NotNull(message = "customer email is required")
        @Email(message = "customer email is invalid")
        String email,
        Address address) {

}
