package br.com.murilohenzo.ms.user.adapters.inbound.queue;

import br.com.murilohenzo.ms.user.domain.entities.EventType;
import br.com.murilohenzo.ms.user.domain.ports.EventPublisherPort;
import br.com.murilohenzo.ms.user.infrastructure.config.rabbit.RabbitProperties;
import br.com.murilohenzo.ms.user.presentation.representation.UserEventRepresentation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Slf4j
@RequiredArgsConstructor
public class UserEventPublisher implements EventPublisherPort {

    private final RabbitTemplate rabbitTemplate;

    private final RabbitProperties properties;

    @Override
    public void publishEvent(UserEventRepresentation userEventRepresentation, EventType eventType) {
        log.debug("[D20] - PUBLICANDO EVENTO:{}", userEventRepresentation);
        userEventRepresentation.setEventType(UserEventRepresentation.EventTypeEnum.valueOf(eventType.toString()));
        rabbitTemplate.convertAndSend(properties.getExchangeUserEvent(), properties.getRoutingKey(), userEventRepresentation);
    }
}
