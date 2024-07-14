package br.com.murilohenzo.ms.user.infrastructure.config.rabbit;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "rabbit")
public class RabbitProperties {

    private String hostname;
    private Integer port;
    private String username;
    private String password;
    private String routingKey;
    private String exchangeUserEvent;
    private String eventQueue;
}
