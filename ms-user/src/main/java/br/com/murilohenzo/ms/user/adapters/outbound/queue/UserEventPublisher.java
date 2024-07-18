package br.com.murilohenzo.ms.user.adapters.outbound.queue;

import br.com.murilohenzo.ms.user.domain.entities.EventType;
import br.com.murilohenzo.ms.user.domain.ports.EventPublisherPort;
import br.com.murilohenzo.ms.user.infrastructure.config.rabbit.RabbitProperties;
import br.com.murilohenzo.ms.user.presentation.representation.UserEventRepresentation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Slf4j
@RequiredArgsConstructor
public class UserEventPublisher implements EventPublisherPort<UserEventRepresentation> {

    private final RabbitTemplate rabbitTemplate;

    private final RabbitProperties properties;

    @Override
    public void publishEvent(UserEventRepresentation userEventRepresentation, EventType eventType) {
        userEventRepresentation.setEventType(UserEventRepresentation.EventTypeEnum.valueOf(eventType.toString()));
        log.info("[D20] - PUBLICANDO EVENTO:{}", userEventRepresentation);
        rabbitTemplate.convertAndSend(properties.getExchangeUserEvent(), properties.getRoutingKey(), userEventRepresentation);
    }
}
