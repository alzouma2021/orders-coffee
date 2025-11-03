package com.order.coffee.infrastructure.Job;

import com.order.coffee.domain.entities.OrderCoffee;
import com.order.coffee.domain.model.OrderStatus;
import com.order.coffee.domain.ports.OrderCoffeeRepository;
import com.order.coffee.infrastructure.model.Order;
import com.order.coffee.infrastructure.notification.SseEmitterService;
import org.jobrunr.jobs.annotations.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Component
public class OrderCoffeeJobExecution {

    private static final Logger logger = LoggerFactory.getLogger(OrderCoffeeJobExecution.class);

    private final OrderCoffeeRepository orderCoffeeRepository;
    private final SseEmitterService sseEmitterService;

    public OrderCoffeeJobExecution(OrderCoffeeRepository orderCoffeeRepository, SseEmitterService sseEmitterService) {
        this.orderCoffeeRepository = orderCoffeeRepository;
        this.sseEmitterService = sseEmitterService;
    }

    @Job(name = "Execution order coffee")
    public void executionQueue(UUID uuid) {
        try {
            OrderCoffee orderCoffee = orderCoffeeRepository.findOrderCoffeeById(uuid);

            logger.info("#### Execute order coffee with orderCoffee:"+orderCoffee);

            orderCoffee.toReturn();
            orderCoffeeRepository.save(orderCoffee);

            SseEmitter emitter = sseEmitterService.getEmitters().get(orderCoffee.getType());
            if (Objects.nonNull(emitter)) {
                logger.info("#### Send notification with coffeeType:"+orderCoffee.getType());
                emitter.send(
                    SseEmitter.event()
                        .name(OrderStatus.RETURNED.name())
                        .data(new Order(
                            orderCoffee.getUuid(),
                            orderCoffee.getCustomer().name(),
                            orderCoffee.getCustomer().phoneNumber(),
                            orderCoffee.getType().name(),
                            orderCoffee.getStatus().name(),
                            LocalDateTime.now().toString()
                        ), MediaType.APPLICATION_JSON)
                );
            }
        } catch (Exception e) {
            logger.info(" Execution order coffee failed with error" + e.getMessage());
        }
    }
}
