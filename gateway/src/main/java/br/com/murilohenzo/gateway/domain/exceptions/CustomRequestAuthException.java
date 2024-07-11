package br.com.murilohenzo.gateway.domain.exceptions;

public class CustomRequestAuthException extends RuntimeException {
    public CustomRequestAuthException(String message) {
        super(message);
    }
}
