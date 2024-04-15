package com.murilohenzo.petapi.domain.services;

import com.murilohenzo.petapi.domain.models.UserRefDomain;
import com.murilohenzo.petapi.domain.ports.PetPersistencePort;
import com.murilohenzo.petapi.domain.ports.UserRefPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class UserRefServicePortImpl {
  
  private final UserRefPersistencePort userRefRepository;
  private final PetPersistencePort petRepository;
  
  @Transactional
  public UserRefDomain save(UserRefDomain user) {
    return userRefRepository.save(user);
  }
  
  @Transactional(readOnly = true)
  public Optional<UserRefDomain> findById(UUID userId) {
    return userRefRepository.findById(userId);
  }
  
  @Transactional
  public void delete(UUID userId) {
    userRefRepository.delete(userId);
    petRepository.deletePetByUserId(userId);
  }
}
