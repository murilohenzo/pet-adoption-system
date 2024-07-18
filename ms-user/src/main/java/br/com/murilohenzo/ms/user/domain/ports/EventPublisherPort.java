package br.com.murilohenzo.ms.user.domain.ports;

import br.com.murilohenzo.ms.user.domain.entities.EventType;

public interface EventPublisherPort<T> {
    void publishEvent(T event, EventType eventType);
}
