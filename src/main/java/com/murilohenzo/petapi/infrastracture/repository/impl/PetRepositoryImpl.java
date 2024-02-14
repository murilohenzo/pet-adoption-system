package com.murilohenzo.petapi.infrastracture.repository.impl;

import com.murilohenzo.petapi.domain.entities.Pet;
import com.murilohenzo.petapi.domain.entities.Status;
import com.murilohenzo.petapi.domain.repository.PetRepository;
import com.murilohenzo.petapi.infrastracture.repository.SpringDataPetRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class PetRepositoryImpl implements PetRepository {

  private final SpringDataPetRepository repository;

  @Override
  public Pet save(Pet pet) {
    return repository.save(pet);
  }

  @Override
  public void update(Pet pet) {
    repository.save(pet);
  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }

  @Override
  public Optional<Pet> findById(Long petId) {
    return repository.findById(petId);
  }

  @Override
  public List<Pet> findByStatus(Status status) {
    return repository.findByStatus(status);
  }

}
