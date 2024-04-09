package com.murilohenzo.petapi.domain.ports;

import com.murilohenzo.petapi.domain.models.UserRefDomain;

import java.util.Optional;
import java.util.UUID;

public interface UserRefPersistencePort {
  
  Optional<UserRefDomain> findById(UUID userId);
  UserRefDomain save(UserRefDomain user);
  void delete(UUID userId);  
  
}
