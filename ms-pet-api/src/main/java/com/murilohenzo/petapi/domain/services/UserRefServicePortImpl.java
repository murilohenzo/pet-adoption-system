package com.murilohenzo.petapi.domain.services;

import com.murilohenzo.petapi.domain.models.UserDomain;
import com.murilohenzo.petapi.domain.ports.PetPersistencePort;
import com.murilohenzo.petapi.domain.ports.UserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
public class UserRefServicePortImpl {
  
  private final UserPersistencePort userRefRepository;
  private final PetPersistencePort petRepository;
  
  @Transactional
  public UserDomain save(UserDomain user) {
    return userRefRepository.save(user);
  }
  
  @Transactional(readOnly = true)
  public Optional<UserDomain> findById(Long userId) {
    return userRefRepository.findById(userId);
  }
  
  @Transactional
  public void delete(Long userId) {
    userRefRepository.delete(userId);
    petRepository.deletePetByUserId(userId);
  }
}
