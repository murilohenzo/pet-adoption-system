package br.com.murilohenzo.gateway.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenRepresentation (
        @JsonProperty("access_token")
        String accessToken,
        @JsonProperty("expires_in")
        Integer expiresIn,
        @JsonProperty("refresh_expires_in")
        Integer refreshExpiresIn,
        @JsonProperty("token_type")
        String tokenType,
        @JsonProperty("not-before-policy")
        Integer notBeforePolicy,
        @JsonProperty("session_state")
        String sessionState, String scope) {}
