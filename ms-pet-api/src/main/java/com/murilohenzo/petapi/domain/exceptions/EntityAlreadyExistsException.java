package com.murilohenzo.petapi.domain.exceptions;

public class EntityAlreadyExistsException extends RuntimeException {
  public EntityAlreadyExistsException(String message) {
    super(message);
  }
}
