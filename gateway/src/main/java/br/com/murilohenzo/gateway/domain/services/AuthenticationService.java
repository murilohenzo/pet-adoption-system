package br.com.murilohenzo.gateway.domain.services;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.murilohenzo.gateway.config.SecurityProperties;
import br.com.murilohenzo.gateway.domain.entities.Credentials;
import br.com.murilohenzo.gateway.domain.entities.TokenRepresentation;
import reactor.core.publisher.Mono;

@Service
@EnableConfigurationProperties(SecurityProperties.class)
public class AuthenticationService {

    private final SecurityProperties properties;

    public AuthenticationService(SecurityProperties properties) {
        this.properties = properties;
    }

    private final WebClient webClient = WebClient.builder().build();

    public Mono<TokenRepresentation> generateTokenV1(Credentials credentials) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", properties.getResourceClient().getClientId());
        formData.add("client_secret", properties.getResourceClient().getClientSecret());
        formData.add("grant_type", properties.getResourceClient().getGrantType());
        formData.add("scope", properties.getResourceClient().getScope());
        formData.add("username", credentials.username());
        formData.add("password", credentials.password());

        return webClient.post()
                .uri(properties.getResourceClient().getJwtUrl())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(TokenRepresentation.class);
    }
}

