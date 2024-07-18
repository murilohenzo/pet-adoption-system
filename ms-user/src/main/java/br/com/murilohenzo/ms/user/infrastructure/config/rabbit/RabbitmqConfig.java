package br.com.murilohenzo.ms.user.infrastructure.config.rabbit;

import br.com.murilohenzo.ms.user.adapters.outbound.queue.UserEventPublisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
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
        return new SimplePropertyValueConnectionNameStrategy("ms-user");
    }

    /**
     * Cria e configura a fábrica de conexões do RabbitMQ.
     *
     * @param connectionNameStrategy a estratégia de nome de conexão
     * @return a ConnectionFactory configurada
     */
    @Bean
    public ConnectionFactory connectionFactory(ConnectionNameStrategy connectionNameStrategy) {
        log.info("[I32] - INICIALIZANDO CONEXAO COM RABBITMQ");
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setHost(properties.getHostname());
        cachingConnectionFactory.setPort(properties.getPort());
        cachingConnectionFactory.setUsername(properties.getUsername());
        cachingConnectionFactory.setPassword(properties.getPassword());
        cachingConnectionFactory.setConnectionNameStrategy(connectionNameStrategy);
        return cachingConnectionFactory;
    }

    /**
     * Cria a fila do RabbitMQ.
     *
     * @return a fila configurada
     */
    @Bean
    public Queue queue() {
        return new Queue(properties.getEventQueue());
    }

    /**
     * Cria a troca de tópicos do RabbitMQ.
     *
     * @return a TopicExchange configurada
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(properties.getExchangeUserEvent());
    }

    /**
     * Cria a ligação entre a fila e a troca de tópicos usando a chave de roteamento.
     *
     * @param queue        a fila do RabbitMQ
     * @param topicExchange a troca de tópicos do RabbitMQ
     * @return a ligação configurada
     */
    @Bean
    public Binding binding(Queue queue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(queue)
                .to(topicExchange)
                .with(properties.getRoutingKey());
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

    @Bean
    public UserEventPublisher userEventPublisher(RabbitTemplate rabbitTemplate) {
        return new UserEventPublisher(rabbitTemplate, properties);
    }
}
