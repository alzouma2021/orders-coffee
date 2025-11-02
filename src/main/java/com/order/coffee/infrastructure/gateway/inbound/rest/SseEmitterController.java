package com.order.coffee.infrastructure.gateway.inbound.rest;

import com.order.coffee.domain.model.CoffeeType;
import com.order.coffee.infrastructure.notification.SseEmitterService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@CrossOrigin("*")
@RestController
@RequestMapping("/sse")
public class SseEmitterController {

    private final SseEmitterService emitterService;

    public SseEmitterController(SseEmitterService emitterService) {
        this.emitterService = emitterService;
    }

    @GetMapping(value = "/connect", produces = MediaType.APPLICATION_JSON_VALUE)
    public SseEmitter getSseEmitter(@RequestParam("type") CoffeeType type){
        return emitterService.connect(type);
    }
}
