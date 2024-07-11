package br.com.murilohenzo.gateway.domain.exceptions;

public class RateLimitRequestException extends RuntimeException {
    public RateLimitRequestException(String message) {
        super(message);
    }
}
