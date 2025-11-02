package com.order.coffee.application.model.request;

import java.util.Objects;

public record CreateOrderCoffeeRequest(String coffeeType, String customerName, String customerPhoneNumber) {

    public CreateOrderCoffeeRequest{
        Objects.requireNonNull(coffeeType);
        Objects.requireNonNull(customerName);
        Objects.requireNonNull(customerPhoneNumber);
    }
}
