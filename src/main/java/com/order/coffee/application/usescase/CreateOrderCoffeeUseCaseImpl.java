package com.order.coffee.application.usescase;

import com.order.coffee.application.model.OrderCoffeeEvent;
import com.order.coffee.application.model.request.CreateOrderCoffeeRequest;
import com.order.coffee.application.model.response.CreateOrderCoffeeResponse;
import com.order.coffee.application.ports.CreateOrderCoffeeUseCase;
import com.order.coffee.domain.entities.OrderCoffee;
import com.order.coffee.domain.model.CoffeeType;
import com.order.coffee.domain.model.Customer;
import com.order.coffee.domain.ports.OrderCoffeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CreateOrderCoffeeUseCaseImpl implements CreateOrderCoffeeUseCase {

    private static final Logger logger = LoggerFactory.getLogger(CreateOrderCoffeeUseCaseImpl.class);

    private final OrderCoffeeRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    public CreateOrderCoffeeUseCaseImpl(OrderCoffeeRepository repository, ApplicationEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public List<CreateOrderCoffeeResponse> createOrders(List<CreateOrderCoffeeRequest> requests) {
        if (CollectionUtils.isEmpty(requests)) return new ArrayList<>();

        logger.info("#### Begin handle create orders coffee with ordersList:"+ requests);

        return requests
            .stream()
            .map(request -> {
                OrderCoffee orderCoffee = new OrderCoffee(
                        new Customer(request.customerName(), request.customerPhoneNumber()),
                        CoffeeType.valueOf(request.coffeeType())
                );

                orderCoffee.toInitiate();

                OrderCoffee orderCoffeeCreated = repository.save(orderCoffee);

                eventPublisher.publishEvent(new OrderCoffeeEvent(this, orderCoffeeCreated));

                return buildResponse(orderCoffeeCreated);
            }).collect(Collectors.toList());
    }

    private CreateOrderCoffeeResponse buildResponse(OrderCoffee orderCoffeeCreated) {
        return new CreateOrderCoffeeResponse(
                orderCoffeeCreated.getUuid(),
                orderCoffeeCreated.getStatus().name(),
                orderCoffeeCreated.getCreationDate()
        );
    }
}
