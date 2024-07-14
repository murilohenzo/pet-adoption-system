package br.com.murilohenzo.ms.user.domain.ports;

import br.com.murilohenzo.ms.user.domain.entities.EventType;
import br.com.murilohenzo.ms.user.presentation.representation.UserEventRepresentation;

public interface EventPublisherPort {
    void publishEvent(UserEventRepresentation userEventRepresentation, EventType eventType);
}
