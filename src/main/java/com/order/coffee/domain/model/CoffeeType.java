package com.order.coffee.domain.model;


public enum CoffeeType {
    STANDARDT(1, 200d),
    ESPRESSO(2, 300d),
    LATTE(3, 400d),
    CAPPUCCINO(4, 500d),
    AMERICANO(5, 600d);

    private final int ExecutionTimeMinuts;
    private final double price;

    CoffeeType(int executionTimeMinuts, Double price) {
        ExecutionTimeMinuts = executionTimeMinuts;
        this.price = price;
    }

    public int getExecutionTimeMinuts() {
        return ExecutionTimeMinuts;
    }

    public Double getPrice() {
        return price;
    }
}

