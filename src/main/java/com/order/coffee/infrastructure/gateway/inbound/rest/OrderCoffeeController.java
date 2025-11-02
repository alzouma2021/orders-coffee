package com.order.coffee.infrastructure.gateway.inbound.rest;

import com.order.coffee.application.model.request.CreateOrderCoffeeRequest;
import com.order.coffee.application.model.response.CreateOrderCoffeeResponse;
import com.order.coffee.application.ports.CreateOrderCoffeeUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/orders-coffee")
public class OrderCoffeeController {

    private static final Logger logger = LoggerFactory.getLogger(OrderCoffeeController.class);

    private final CreateOrderCoffeeUseCase createOrderCoffeeUseCase;

    public OrderCoffeeController(CreateOrderCoffeeUseCase createOrderCoffeeUseCase) {
        this.createOrderCoffeeUseCase = createOrderCoffeeUseCase;
    }

    @PostMapping
    public ResponseEntity<List<CreateOrderCoffeeResponse>> create(@RequestBody List<CreateOrderCoffeeRequest> requests) {
        logger.info("#### Begin handle receive order coffee requests with request:"+requests.toString());

        List<CreateOrderCoffeeResponse> responses = createOrderCoffeeUseCase.createOrders(requests);

        logger.info("#### End handle receive order coffee requests with responses:"+ responses.toString());

        return new ResponseEntity<>(
                responses,
                HttpStatus.CREATED
        );
    }
}
