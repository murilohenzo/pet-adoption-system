package com.murilohenzo.petapi.config.rabbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.murilohenzo.petapi.adapters.inbound.queue.UserEventListener;
import com.murilohenzo.petapi.adapters.mapper.UserMapper;
import com.murilohenzo.petapi.domain.services.UserRefServicePortImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.amqp.rabbit.connection.SimplePropertyValueConnectionNameStrategy;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Configuração do RabbitMQ para o microserviço de usuário.
 * Define a fábrica de conexão, template de mensagem, filas, troca de tópicos e conversores de mensagem.
 */
@Slf4j
@EnableRabbit
@Configuration
@EnableConfigurationProperties(RabbitProperties.class)
@RequiredArgsConstructor
public class RabbitmqConfig {

  private final RabbitProperties properties;

  /**
   * Cria e configura o RabbitTemplate.
   *
   * @return o RabbitTemplate configurado
   */
  @Bean
  public RabbitTemplate rabbitTemplate() {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory(connectionNameStrategy()));
    rabbitTemplate.setMessageConverter(messageConverter());
    return rabbitTemplate;
  }

  /**
   * Define a estratégia de nome de conexão.
   *
   * @return a ConnectionNameStrategy configurada
   */
  @Bean
  public ConnectionNameStrategy connectionNameStrategy() {
    return new SimplePropertyValueConnectionNameStrategy("ms-pets");
  }

  /**
   * Cria e configura a fábrica de conexões do RabbitMQ.
   *
   * @param connectionNameStrategy a estratégia de nome de conexão
   * @return a ConnectionFactory configurada
   */
  @Bean
  public ConnectionFactory connectionFactory(ConnectionNameStrategy connectionNameStrategy) {
    log.info("[I66] - INICIALIZANDO CONEXAO COM RABBITMQ");
    CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
    cachingConnectionFactory.setHost(properties.getHostname());
    cachingConnectionFactory.setPort(properties.getPort());
    cachingConnectionFactory.setUsername(properties.getUsername());
    cachingConnectionFactory.setPassword(properties.getPassword());
    cachingConnectionFactory.setConnectionNameStrategy(connectionNameStrategy);
    return cachingConnectionFactory;
  }

  /**
   * Cria o conversor de mensagem Jackson para o RabbitMQ.
   *
   * @return o Jackson2JsonMessageConverter configurado
   */
  @Bean
  public Jackson2JsonMessageConverter messageConverter() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    return new Jackson2JsonMessageConverter(objectMapper);
  }

  /**
   * Cria e configura o listener de eventos de usuário.
   *
   * @param userRefServicePort o serviço de referência de usuário
   * @param userMapper o mapper de usuário
   * @return o UserEventListener configurado
   */
  @Bean
  @Scope("singleton")
  public UserEventListener userEventListener(UserRefServicePortImpl userRefServicePort, UserMapper userMapper) {
    log.info("[I88] - ATIVANDO CONSUMIDOR DA FILA");
    return new UserEventListener(userRefServicePort, userMapper);
  }
}
