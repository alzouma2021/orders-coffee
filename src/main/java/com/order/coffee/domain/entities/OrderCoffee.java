package com.order.coffee.domain.entities;

import com.order.coffee.domain.model.CoffeeType;
import com.order.coffee.domain.model.Customer;
import com.order.coffee.domain.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class OrderCoffee {
    private final UUID uuid;
    private final Customer customer;
    private OrderStatus status;
    private final CoffeeType type;
    private final LocalDateTime creationDate;

    public OrderCoffee(Customer customer, CoffeeType type) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(customer);
        this.customer = customer;
        this.uuid = UUID.randomUUID();
        this.type = type;
        creationDate = LocalDateTime.now();
    }

    public OrderCoffee(UUID uuid, Customer customer, CoffeeType type, OrderStatus status, LocalDateTime creationDate) {
        this.uuid = uuid;
        this.customer = customer;
        this.type = type;
        this.status = status;
        this.creationDate = creationDate;
    }

    public void toInitiate() {
        if(Objects.nonNull(getStatus()))
            throw new RuntimeException("order.coffee.can.be.created");

        this.status = OrderStatus.INITIATED;
    }

    public void toReject() {
        if (!OrderStatus.STARTED.equals(getStatus()))
            throw new RuntimeException("Order.can.not.be.rejected: status -> " + getStatus());

        this.status = OrderStatus.REJECTED;
    }

    public void toReturn() {
        if (!OrderStatus.STARTED.equals(getStatus()))
            throw new RuntimeException("Order.can.not.be.return: status -> " + getStatus());

        this.status = OrderStatus.RETURNED;
    }

    public void toStart() {
        if (!OrderStatus.INITIATED.equals(getStatus()))
            throw new RuntimeException("Order.can.not.be.start: status -> " + getStatus());

        this.status = OrderStatus.STARTED;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Customer getCustomer() {
        return customer;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public CoffeeType getType() {
        return type;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    @Override
    public String toString() {
        return "OrderCoffee{" +
                "uuid=" + uuid +
                ", customer=" + customer +
                ", status=" + status +
                ", type=" + type +
                ", creationDate=" + creationDate +
                '}';
    }
}
