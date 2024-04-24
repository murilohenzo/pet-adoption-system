package com.murilohenzo.petapi.domain.ports;

import com.murilohenzo.petapi.domain.models.UserDomain;

import java.util.Optional;

public interface UserPersistencePort {
  
  Optional<UserDomain> findById(Long userId);
  UserDomain save(UserDomain user);
  void delete(Long userId);  
  
}
