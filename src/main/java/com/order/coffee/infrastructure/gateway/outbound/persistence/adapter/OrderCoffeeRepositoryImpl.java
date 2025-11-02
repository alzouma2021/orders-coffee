package com.order.coffee.infrastructure.gateway.outbound.persistence.adapter;

import com.order.coffee.domain.entities.OrderCoffee;
import com.order.coffee.domain.ports.OrderCoffeeRepository;
import com.order.coffee.infrastructure.gateway.outbound.persistence.entities.OrderCoffeeEntity;
import com.order.coffee.infrastructure.gateway.outbound.persistence.repository.OrderCoffeeJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
class OrderCoffeeRepositoryImpl implements OrderCoffeeRepository {

    private final OrderCoffeeJpaRepository repository;

    public OrderCoffeeRepositoryImpl(OrderCoffeeJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public OrderCoffee save(OrderCoffee orderCoffee) {
        return Optional.of(repository.saveAndFlush(OrderCoffeeEntity.toEntity(orderCoffee)))
                .map(OrderCoffeeEntity::toDomain)
                .orElseThrow(() -> new RuntimeException("create.order.coffee.failed"));
    }

    @Override
    public OrderCoffee findOrderCoffeeById(UUID uuid) {
        return repository.findById(uuid).map(OrderCoffeeEntity::toDomain)
                .orElseThrow(() -> new RuntimeException("Order.coffee.not.found"));
    }
}
