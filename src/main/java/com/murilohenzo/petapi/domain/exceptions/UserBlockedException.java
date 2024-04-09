package com.murilohenzo.petapi.domain.exceptions;

public class UserBlockedException extends RuntimeException {
  public UserBlockedException(String message) {
    super(message);
  }
}
