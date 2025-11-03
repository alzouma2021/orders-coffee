package com.order.coffee.domain.model;

public record Customer(String name, String phoneNumber) {

    public Customer{
        if(name == null || name.isBlank())
            throw new IllegalArgumentException("Customer name must be define");

        if(phoneNumber == null || phoneNumber.isBlank())
            throw new IllegalArgumentException("Customer phone number must be define");
    }
}
