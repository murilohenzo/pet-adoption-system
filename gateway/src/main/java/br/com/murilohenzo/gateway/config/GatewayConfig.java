package br.com.murilohenzo.gateway.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final CustomTokenRelayGatewayFilterFactory customTokenRelayGatewayFilterFactory;

    private final String CUSTOM_AUTH = "custom_auth";

    @Bean
    public WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {

        log.info("[I21] - CustomRouter ");

        return builder.routes()
                .route("users", r -> r.path("/users")
                        .and()
                        .method(HttpMethod.POST)
                        .filters(f -> f.filter(customTokenRelayGatewayFilterFactory.apply(CUSTOM_AUTH)))
                        .uri("http://localhost:3000"))
                .build();
    }
}
