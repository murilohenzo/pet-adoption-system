package com.murilohenzo.petapi.domain.ports;

import com.murilohenzo.petapi.domain.models.UserDomain;

import java.util.Optional;

public interface UserPersistencePort {
  
  Optional<UserDomain> findById(Long userId);
  Optional<UserDomain> findByReferenceId(String referenceId);
  void save(UserDomain user);
  void delete(String referenceId);
}
