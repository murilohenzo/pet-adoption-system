package com.murilohenzo.petapi.domain.ports;

import com.murilohenzo.petapi.domain.models.PetDomain;
import com.murilohenzo.petapi.domain.models.enums.PetStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PetPersistencePort {
  
  List<PetDomain> findAll();
  List<PetDomain> findByStatus(PetStatus petStatus);
  List<PetDomain> findAllByUserId(UUID userID);
  Optional<PetDomain> findById(UUID id);
  PetDomain save(PetDomain pet);
  void update(PetDomain pet);
  void delete(UUID id);
  void deletePetByUserId(UUID userID);
  
}
