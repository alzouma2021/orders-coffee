package com.order.coffee.application.model;

import com.order.coffee.domain.entities.OrderCoffee;
import org.springframework.context.ApplicationEvent;

import java.io.Serial;
import java.io.Serializable;

public class OrderCoffeeEvent extends ApplicationEvent implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final OrderCoffee orderCoffee;

    public OrderCoffeeEvent(Object source, OrderCoffee orderCoffee) {
        super(source);
        this.orderCoffee = orderCoffee;
    }

    public OrderCoffee getOrderCoffee() {
        return orderCoffee;
    }
}
