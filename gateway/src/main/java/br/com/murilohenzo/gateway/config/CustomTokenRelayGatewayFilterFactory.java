package br.com.murilohenzo.gateway.config;

import br.com.murilohenzo.gateway.domain.entities.TokenRepresentation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@EnableConfigurationProperties(SecurityProperties.class)
public class CustomTokenRelayGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    private static final String GRANT_TYPE_KEY = "grant_type";

    private static final String CLIENT_ID = "client_id";

    private static final String CLIENT_SECRET = "client_secret";

    private static final String FLOW_CLIENT_CREDENTIALS = "client_credentials";

    private final WebClient webClient = WebClient.builder().build();

    private final SecurityProperties properties;

    public CustomTokenRelayGatewayFilterFactory(SecurityProperties properties) {
        this.properties = properties;
    }

    private Mono<TokenRepresentation> generateApplicationTokenV1() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add(CLIENT_ID, properties.getResourceClient().getClientId());
        formData.add(CLIENT_SECRET, properties.getResourceClient().getClientSecret());
        formData.add(GRANT_TYPE_KEY, FLOW_CLIENT_CREDENTIALS);

        return webClient.post()
                .uri(properties.getResourceClient().getJwtUrl())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(TokenRepresentation.class);
    }

    @Override
    public GatewayFilter apply(Object config) {

        log.debug("[D53] - CustomTokenRelayGatewayFilterFactory: {}", config);

        return (exchange, chain) -> generateApplicationTokenV1()
                .map(tokenRepresentation -> {
                    exchange.getRequest().mutate()
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenRepresentation.accessToken())
                            .build();
                    return exchange;
                })
                .flatMap(chain::filter);
    }
}