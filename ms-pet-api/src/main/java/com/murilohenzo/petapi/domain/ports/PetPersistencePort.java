package com.murilohenzo.petapi.domain.ports;

import com.murilohenzo.petapi.domain.models.PetDomain;
import com.murilohenzo.petapi.domain.models.enums.PetStatus;

import java.util.List;
import java.util.Optional;

public interface PetPersistencePort {
  
  List<PetDomain> findAll();
  List<PetDomain> findByStatus(PetStatus petStatus);
  List<PetDomain> findAllByUserId(Long userID);
  Optional<PetDomain> findById(Long id);
  PetDomain save(PetDomain petDomain);
  void saveUserPet(PetDomain petDomain);
  void update(PetDomain pet);
  void deletePetById(Long id);
  void deletePetByUserId(Long userID);
}
