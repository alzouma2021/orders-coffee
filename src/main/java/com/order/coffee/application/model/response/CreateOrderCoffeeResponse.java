package com.order.coffee.application.model.response;

import java.time.LocalDateTime;
import java.util.Objects;

public record CreateOrderCoffeeResponse(java.util.UUID uuid, String orderStatus, LocalDateTime creationDate) {

    public CreateOrderCoffeeResponse{
        Objects.requireNonNull(uuid);
        Objects.requireNonNull(orderStatus);
    }
}
