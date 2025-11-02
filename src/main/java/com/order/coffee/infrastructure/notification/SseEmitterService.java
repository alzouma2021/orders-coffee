package com.order.coffee.infrastructure.notification;

import com.order.coffee.domain.model.CoffeeType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class SseEmitterService {

    private final Map<CoffeeType, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter connect(CoffeeType type) {
        SseEmitter emitter = new SseEmitter(60_000L);
        emitters.put(type,emitter);

        emitter.onCompletion(() -> emitters.remove(type));
        emitter.onTimeout(() -> emitters.remove(type));
        emitter.onError((e) -> emitters.remove(type));

        return emitter;
    }

    public Map<CoffeeType, SseEmitter> getEmitters() {
        return emitters;
    }
}
