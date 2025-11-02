package com.order.coffee.infrastructure.gateway.outbound.persistence.entities;

import com.order.coffee.domain.entities.OrderCoffee;
import com.order.coffee.domain.model.CoffeeType;
import com.order.coffee.domain.model.Customer;
import com.order.coffee.domain.model.OrderStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "order_coffee")
public class OrderCoffeeEntity {

    @Id
    private UUID id;
    @Embedded
    private CustomerEmbedded customerEmbedded;
    @Enumerated(EnumType.STRING)
    private CoffeeType type;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private LocalDateTime creationDate;

    public OrderCoffeeEntity() {
    }

    public OrderCoffeeEntity(UUID id, CustomerEmbedded customerEmbedded, CoffeeType type, OrderStatus status, LocalDateTime creationDate) {
        this.id = id;
        this.customerEmbedded = customerEmbedded;
        this.type = type;
        this.status = status;
        this.creationDate = creationDate;
    }

    public OrderCoffee toDomain() {
        return new OrderCoffee(
                getId(),
                new Customer(getCustomerEmbedded().getName(), getCustomerEmbedded().getPhoneNumber()),
                getType(),
                getStatus(),
                getCreationDate()
        );
    }

    public static OrderCoffeeEntity toEntity(OrderCoffee orderCoffee) {
        return new OrderCoffeeEntity(
                orderCoffee.getUuid(),
                new CustomerEmbedded(orderCoffee.getCustomer().name(), orderCoffee.getCustomer().phoneNumber()),
                orderCoffee.getType(),
                orderCoffee.getStatus(),
                orderCoffee.getCreationDate()
        );
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public CustomerEmbedded getCustomerEmbedded() {
        return customerEmbedded;
    }

    public void setCustomerEmbedded(CustomerEmbedded customerEmbedded) {
        this.customerEmbedded = customerEmbedded;
    }

    public CoffeeType getType() {
        return type;
    }

    public void setType(CoffeeType type) {
        this.type = type;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
