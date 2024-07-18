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
  public void save(UserDomain user) {
    userRefRepository.save(user);
  }
  
  @Transactional(readOnly = true)
  public Optional<UserDomain> findById(Long userId) {
    return userRefRepository.findById(userId);
  }
  
  @Transactional
  public void delete(String referenceId) {
    var user = userRefRepository.findByReferenceId(referenceId);
    if (user.isPresent()) {
      petRepository.removeUserFromPets(user.get().getId());
      userRefRepository.delete(referenceId);
    }
  }
}
