package com.murilohenzo.petapi.domain.services;

import com.murilohenzo.petapi.config.Messages;
import com.murilohenzo.petapi.domain.exceptions.EntityAlreadyExistsException;
import com.murilohenzo.petapi.domain.exceptions.UserBlockedException;
import com.murilohenzo.petapi.domain.models.PetDomain;
import com.murilohenzo.petapi.domain.models.enums.PetStatus;
import com.murilohenzo.petapi.domain.models.enums.UserStatus;
import com.murilohenzo.petapi.domain.ports.PetPersistencePort;
import com.murilohenzo.petapi.domain.ports.UserPersistencePort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class PetServicePortImpl {

  private final PetPersistencePort petPersistencePort;
  private final UserPersistencePort userPersistencePort;

  @Transactional
  public PetDomain create(PetDomain pet) {
    pet.setPetStatus(PetStatus.AVAILABLE);
    return petPersistencePort.save(pet);
  }
  
  @Transactional(readOnly = true)
  public List<PetDomain> findAll() {
    return petPersistencePort.findAll();
  }

  @Transactional(readOnly = true)
  public List<PetDomain> findPetsByStatus(PetStatus petStatus) {
    return petPersistencePort.findByStatus(petStatus);
  }

  @Transactional(readOnly = true)
  public Optional<PetDomain> findById(Long petId) {
    return petPersistencePort.findById(petId);
  }

  @Transactional
  public void delete(Long petId) {
    var existsPet = petPersistencePort.findById(petId);
    if (existsPet.isEmpty()) {
      throw new EntityNotFoundException(Messages.getString("PetHandler.03"));
    }
    petPersistencePort.deletePetById(petId);
  }

  @Transactional
  public PetDomain update(Long petId, PetDomain pet) {
    var existsPet = petPersistencePort.findById(petId);
    if (existsPet.isEmpty()) {
      throw new EntityNotFoundException(Messages.getString("PetHandler.03"));
    } else {
      existsPet.get().setName(pet.getName());
      existsPet.get().setDescription(pet.getDescription());
      existsPet.get().setPetGender(pet.getPetGender());
      return petPersistencePort.save(existsPet.get());
    }
  }
  
  @Transactional(readOnly = true)
  public List<PetDomain> findAllPetsByUser(Long userId) {
    var adoptUser = userPersistencePort.findById(userId);
    if (adoptUser.isEmpty()) {
      throw new EntityNotFoundException(Messages.getString("PetHandler.05"));
    } else {
      return petPersistencePort.findAllByUserId(adoptUser.get().getId());
    }
  }
  
  @Transactional
  public void adoptionPet(Long petId, Long userId) {
    var existPet = petPersistencePort.findById(petId)
      .orElseThrow(() -> new EntityNotFoundException(Messages.getString("PetHandler.03")));

    var adoptUser = userPersistencePort.findById(userId)
      .orElseThrow(() -> new EntityNotFoundException(Messages.getString("PetHandler.02")));
    
    if (existPet.getPetStatus().equals(PetStatus.DONATED)) {
      throw new EntityAlreadyExistsException(Messages.getString("PetHandler.01"));
    }
    
    if (adoptUser.getUserStatus().equals(UserStatus.BLOCKED)) {
      throw new UserBlockedException(Messages.getString("PetHandler.04"));
    }
    
    existPet.setPetStatus(PetStatus.DONATED);
    existPet.setUser(adoptUser);
    petPersistencePort.saveUserPet(existPet);
  }
}
