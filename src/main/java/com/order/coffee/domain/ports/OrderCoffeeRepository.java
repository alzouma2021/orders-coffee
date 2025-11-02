package com.order.coffee.domain.ports;

import com.order.coffee.domain.entities.OrderCoffee;

import java.util.UUID;

public interface OrderCoffeeRepository {
    OrderCoffee save(OrderCoffee orderCoffee);
    OrderCoffee findOrderCoffeeById(UUID uuid);
}
