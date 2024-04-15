package com.murilohenzo.petapi.adapters.outbound.persistence.impl;

import com.murilohenzo.petapi.adapters.outbound.persistence.UserRefJpaRepository;
import com.murilohenzo.petapi.domain.models.UserRefDomain;
import com.murilohenzo.petapi.domain.ports.UserRefPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class UserRefPersistencePortImpl implements UserRefPersistencePort {
  
  private final UserRefJpaRepository userRefJpaRepository;
  // TODO: criar user mapper
//  private final UserRefMapper userRefMapper;

  @Override
  public Optional<UserRefDomain> findById(UUID userId) {
    return Optional.empty();
  }

  @Override
  public UserRefDomain save(UserRefDomain user) {
    return null;
  }

  @Override
  public void delete(UUID userId) {

  }
}
