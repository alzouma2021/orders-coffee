package com.order.coffee.domain.model;

import ch.qos.logback.core.util.StringUtil;

public record Customer(String name, String phoneNumber) {

    public Customer{
        if(StringUtil.isNullOrEmpty(name))
            throw new IllegalArgumentException("Customer name must be define");

        if(StringUtil.isNullOrEmpty(phoneNumber))
            throw new IllegalArgumentException("Customer phone number must be define");
    }
}
