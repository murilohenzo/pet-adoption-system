package com.murilohenzo.petapi.domain.services;

import com.murilohenzo.petapi.domain.exceptions.EntityAlreadyExistsException;
import com.murilohenzo.petapi.domain.exceptions.UserBlockedException;
import com.murilohenzo.petapi.domain.models.PetDomain;
import com.murilohenzo.petapi.domain.models.enums.PetStatus;
import com.murilohenzo.petapi.domain.models.enums.UserStatus;
import com.murilohenzo.petapi.domain.ports.PetPersistencePort;
import com.murilohenzo.petapi.domain.ports.UserRefPersistencePort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class PetServicePortImpl {

  private final PetPersistencePort petPersistencePort;
  private final UserRefPersistencePort userRefPersistencePort;

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
  public Optional<PetDomain> findById(UUID petId) {
    return petPersistencePort.findById(petId);
  }

  @Transactional
  public void delete(UUID petId) {
    var existsPet = petPersistencePort.findById(petId);
    if (existsPet.isEmpty()) {
      throw new EntityNotFoundException("Not found pet with id: " + petId);
    } else {
      petPersistencePort.deletePetByUserId(existsPet.get().getUser().getUserId());
      petPersistencePort.delete(petId);
    }
  }

  @Transactional
  public PetDomain update(UUID petId, PetDomain pet) {
    var existsPet = petPersistencePort.findById(petId);
    if (existsPet.isEmpty()) {
      throw new EntityNotFoundException("Not found pet with id: " + petId);
    } else {
      existsPet.get().setName(pet.getName());
      existsPet.get().setDescription(pet.getDescription());
      existsPet.get().setPetGender(pet.getPetGender());
      return petPersistencePort.save(existsPet.get());
    }
  }
  
  @Transactional(readOnly = true)
  public List<PetDomain> findAllPetsByUser(UUID userId) {
    var adoptUser = userRefPersistencePort.findById(userId);
    if (adoptUser.isEmpty()) {
      throw new EntityNotFoundException("Not found adoptedBy with id: " + userId);
    } else {
      return petPersistencePort.findAllByUserId(adoptUser.get().getUserId());
    }
  }
  
  @Transactional
  public void adoptionPet(UUID petId, UUID userId) {
    var existPet = petPersistencePort.findById(petId)
      .orElseThrow(() -> new EntityNotFoundException("Pet not found with id: " + petId));

    var adoptUser = userRefPersistencePort.findById(userId)
      .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
    
    if (!existPet.getUser().getCourses().isEmpty()) {
      throw new EntityAlreadyExistsException("Pet has already been adopted");
    }
    
    if (adoptUser.getUserStatus().equals(UserStatus.BLOCKED)) {
      throw new UserBlockedException("User is blocked");
    }
    
    existPet.setUser(adoptUser);
    petPersistencePort.save(existPet);
  }
}
