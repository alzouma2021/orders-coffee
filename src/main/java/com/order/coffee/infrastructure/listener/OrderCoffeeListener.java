package com.order.coffee.infrastructure.listener;

import com.order.coffee.application.model.OrderCoffeeEvent;
import com.order.coffee.domain.entities.OrderCoffee;
import com.order.coffee.domain.model.OrderStatus;
import com.order.coffee.domain.ports.OrderCoffeeRepository;
import com.order.coffee.infrastructure.model.Order;
import com.order.coffee.infrastructure.notification.SseEmitterService;
import com.order.coffee.infrastructure.queue.OrderCoffeeQueueManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Objects;

@Component
public class OrderCoffeeListener {

    private static final Logger logger = LoggerFactory.getLogger(OrderCoffeeListener.class);

    private final OrderCoffeeRepository repository;
    private final OrderCoffeeQueueManager orderCoffeeQueueManager;
    private final SseEmitterService sseEmitterService;


    public OrderCoffeeListener(OrderCoffeeRepository repository, OrderCoffeeQueueManager orderCoffeeQueueManager, SseEmitterService sseEmitterService) {
        this.repository = repository;
        this.orderCoffeeQueueManager = orderCoffeeQueueManager;
        this.sseEmitterService = sseEmitterService;
    }

    @EventListener
    @Async
    void handleOrderCoffee(OrderCoffeeEvent event) throws IOException {
        if(Objects.isNull(event) || Objects.isNull(event.getOrderCoffee())) return;

        logger.info("#### Handle order coffee listener with event: "+event);

        OrderCoffee orderCoffee = event.getOrderCoffee();
        if (!orderCoffeeQueueManager.enqueue(orderCoffee)) {
            orderCoffee.toReject();
            repository.save(orderCoffee);
            sendNotification(orderCoffee);
        }
    }



    private void sendNotification(OrderCoffee orderCoffee) throws IOException {
        SseEmitter emitter = sseEmitterService.getEmitters().get(orderCoffee.getType());
        if (Objects.nonNull(emitter)) {
            emitter.send(
                SseEmitter.event()
                    .name(OrderStatus.REJECTED.name())
                    .data(new Order(
                        orderCoffee.getUuid(),
                        orderCoffee.getCustomer().name(),
                        orderCoffee.getCustomer().phoneNumber(),
                        orderCoffee.getType().name(),
                        orderCoffee.getStatus().name()
                    ), MediaType.TEXT_EVENT_STREAM)
            );
        }
    }
}
