package com.murilohenzo.petapi.domain.ports;

public interface UserEventListenPort<T> {
  void handleUserEvent(T event);
}
