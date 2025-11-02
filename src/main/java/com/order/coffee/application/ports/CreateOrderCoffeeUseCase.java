package com.order.coffee.application.ports;

import com.order.coffee.application.model.request.CreateOrderCoffeeRequest;
import com.order.coffee.application.model.response.CreateOrderCoffeeResponse;

import java.util.List;

public interface CreateOrderCoffeeUseCase {
    List<CreateOrderCoffeeResponse> createOrders(List<CreateOrderCoffeeRequest> requests);
}
