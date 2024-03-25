package com.murilohenzo.petapi.core.service;

import com.murilohenzo.petapi.core.domain.PetDomain;
import com.murilohenzo.petapi.core.domain.enums.Status;
import com.murilohenzo.petapi.core.ports.PetPersistencePort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class PetServicePortImpl {

  private final PetPersistencePort repository;

  @Transactional
  public PetDomain create(PetDomain pet) {
    pet.setStatus(Status.AVAILABLE);
    return repository.save(pet);
  }

  @Transactional(readOnly = true)
  public List<PetDomain> findPetsByStatus(Status status) {
    return repository.findByStatus(status);
  }

  @Transactional(readOnly = true)
  public Optional<PetDomain> findById(UUID petId) {
    return repository.findById(petId);
  }

  @Transactional
  public void delete(UUID petId) {
    var existsPet = repository.findById(petId);
    if (existsPet.isEmpty()) {
      throw new EntityNotFoundException("Not found pet with id: " + petId);
    } else {
      repository.delete(petId);
    }
  }

  @Transactional
  public PetDomain update(UUID petId, PetDomain pet) {
    var existsPet = repository.findById(petId);
    if (existsPet.isEmpty()) {
      throw new EntityNotFoundException("Not found pet with id: " + petId);
    } else {
      existsPet.get().setName(pet.getName());
      existsPet.get().setDescription(pet.getDescription());
      existsPet.get().setGender(pet.getGender());
      return repository.save(existsPet.get());
    }
  } 
}
