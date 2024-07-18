package com.murilohenzo.petapi.adapters.inbound.queue;

import com.murilohenzo.petapi.adapters.mapper.UserMapper;
import com.murilohenzo.petapi.domain.models.enums.EventType;
import com.murilohenzo.petapi.domain.ports.UserEventListenPort;
import com.murilohenzo.petapi.domain.services.UserRefServicePortImpl;
import com.murilohenzo.petapi.presentation.representation.UserEventRepresentation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;

@Slf4j
@RequiredArgsConstructor
public class UserEventListener implements UserEventListenPort<UserEventRepresentation> {
  
  private final UserRefServicePortImpl userRefServicePort;
  private final UserMapper userMapper;

  @Override
  @RabbitListener(bindings = @QueueBinding(
    value = @Queue(
      value = "user.event.queue",
      durable = "true"),
    exchange = @Exchange(
      value = "user.event.exchange",
      type = ExchangeTypes.TOPIC),
    key = "user.event.routing.key")
  )
  public void handleUserEvent(@Payload UserEventRepresentation userEventRepresentation) {

    switch (EventType.valueOf(userEventRepresentation.getEventType().getValue())) {
      case CREATE, UPDATE:
        userRefServicePort.save(userMapper.userEventRepresentationToUserRefDomain(userEventRepresentation));
        break;
      case DELETE:
        log.info("[I39] - EVENTO MAPEADO:{}", userEventRepresentation);
        userRefServicePort.delete(userEventRepresentation.getReferenceId().toString());
        break;
      default:
        log.info("[I43] - EVENTO NAO MAPEADO:{}", userEventRepresentation);
        break;
    }

  }
}
