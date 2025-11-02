package com.order.coffee.infrastructure.model;

import java.util.UUID;

public record Order(UUID uuid, String name, String phoneNumber, String typeCoffee, String status) {
}
