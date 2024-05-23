package br.com.murilohenzo.gateway.adapters.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.murilohenzo.gateway.domain.entities.Credentials;
import br.com.murilohenzo.gateway.domain.entities.TokenRepresentation;
import br.com.murilohenzo.gateway.domain.services.AuthenticationService;
import reactor.core.publisher.Mono;

@RestController
public class GatewayController {

    private final AuthenticationService authenticationService;

    public GatewayController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<TokenRepresentation>> authentication(@RequestBody Credentials credentials) {
        return authenticationService.generateTokenV1(credentials)
                .map(ResponseEntity::ok);
    }
}