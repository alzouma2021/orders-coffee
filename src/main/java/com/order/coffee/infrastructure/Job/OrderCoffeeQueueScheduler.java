package com.order.coffee.infrastructure.Job;

import com.order.coffee.domain.entities.OrderCoffee;
import com.order.coffee.domain.ports.OrderCoffeeRepository;
import com.order.coffee.infrastructure.queue.OrderCoffeeQueueManager;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.scheduling.BackgroundJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class OrderCoffeeQueueScheduler implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(OrderCoffeeQueueScheduler.class);

    private final OrderCoffeeQueueManager orderCoffeeQueueManager;
    private final OrderCoffeeJobExecution orderCoffeeJobExecution;
    private final OrderCoffeeRepository orderCoffeeRepository;


    public OrderCoffeeQueueScheduler(OrderCoffeeQueueManager orderCoffeeQueueManager, OrderCoffeeJobExecution orderCoffeeJobExecution, OrderCoffeeRepository orderCoffeeRepository) {
        this.orderCoffeeQueueManager = orderCoffeeQueueManager;
        this.orderCoffeeJobExecution = orderCoffeeJobExecution;
        this.orderCoffeeRepository = orderCoffeeRepository;
    }


    @Override
    public void run(ApplicationArguments args) {
        BackgroundJob.scheduleRecurrently(Duration.ofSeconds(5), this::scheduleQueue);
    }

    @Job(name = "Schedule process order coffee job")
    public void scheduleQueue() {
        orderCoffeeQueueManager.getQueues()
            .forEach((type, orderCoffees) -> {
                OrderCoffee order;
                while ((order = orderCoffees.poll()) != null) {
                    try {
                        orderCoffeeRepository.findOrderCoffeeById(order.getUuid());
                        order.toStart();
                        orderCoffeeRepository.save(order);
                        OrderCoffee finalOrder = order;
                        String jobRunrId = BackgroundJob.schedule(
                                Instant.now().plus(type.getExecutionTimeMinuts(), ChronoUnit.MINUTES),
                                () -> orderCoffeeJobExecution.executionQueue(finalOrder.getUuid())
                        ).toString();
                        logger.info("#### Schedule execution job order coffee with jobRunrId:"+ jobRunrId + " orderId:"+ order.getUuid());
                    } catch (Exception e) {
                        logger.info(" schedule order coffee queue failed with error" + e.getMessage());
                    }
                }
            });
    }
}
