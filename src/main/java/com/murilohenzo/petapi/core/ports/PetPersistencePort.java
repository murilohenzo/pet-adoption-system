package com.murilohenzo.petapi.core.ports;

import com.murilohenzo.petapi.core.domain.PetDomain;
import com.murilohenzo.petapi.core.domain.enums.Status;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PetPersistencePort {
  List<PetDomain> findByStatus(Status status);

  Optional<PetDomain> findById(UUID id);

  PetDomain save(PetDomain pet);

  void update(PetDomain pet);

  void delete(UUID id);
}
