package com.order.coffee.infrastructure.queue;

import com.order.coffee.domain.entities.OrderCoffee;
import com.order.coffee.domain.model.CoffeeType;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class OrderCoffeeQueueManager  {

    private final Map<CoffeeType, BlockingQueue<OrderCoffee>> queues = new EnumMap<>(CoffeeType.class);


    @PostConstruct
    public void init() {
        Arrays.stream(CoffeeType.values())
            .forEach(type ->  queues.put(type, new LinkedBlockingQueue<>()));
    }

    public boolean enqueue(OrderCoffee orderCoffee) {
        BlockingQueue<OrderCoffee> q = queues.get(orderCoffee.getType());
        if(Objects.isNull(q)) return Boolean.FALSE;
        return q.offer(orderCoffee);
    }

    public Map<CoffeeType, BlockingQueue<OrderCoffee>> getQueues() {
        return queues;
    }
}
