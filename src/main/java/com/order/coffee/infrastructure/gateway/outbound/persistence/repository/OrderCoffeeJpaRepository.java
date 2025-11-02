package com.order.coffee.infrastructure.gateway.outbound.persistence.repository;

import com.order.coffee.infrastructure.gateway.outbound.persistence.entities.OrderCoffeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderCoffeeJpaRepository extends JpaRepository<OrderCoffeeEntity, UUID>{
}
