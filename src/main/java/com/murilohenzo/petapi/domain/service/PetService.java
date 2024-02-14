package com.murilohenzo.petapi.domain.service;

import com.murilohenzo.petapi.domain.entities.Pet;
import com.murilohenzo.petapi.domain.entities.Status;
import com.murilohenzo.petapi.domain.repository.PetRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class PetService {

  private final PetRepository repository;

  @Transactional
  public Pet create(Pet pet) {
    return repository.save(pet);
  }

  @Transactional(readOnly = true)
  public List<Pet> findPetsByStatus(Status status) {
    return repository.findByStatus(status);
  }

  @Transactional(readOnly = true)
  public Optional<Pet> findById(Long petId) {
    return repository.findById(petId);
  }

  @Transactional
  public void deletePetById(Long petId) {
    var existsPet = repository.findById(petId);
    if (existsPet.isEmpty()) {
      throw new EntityNotFoundException("Not found pet with id: " + petId);
    } else {
      repository.delete(petId);
    }
  }

  @Transactional
  public Pet update(Long petId, Pet pet) {
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
