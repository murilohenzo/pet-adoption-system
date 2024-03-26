package com.murilohenzo.petapi.domain.ports;

import com.murilohenzo.petapi.domain.models.PetPhotoDomain;

import java.util.Optional;
import java.util.UUID;

public interface PetPhotoPersistencePort {
  PetPhotoDomain save(PetPhotoDomain photo);

  Optional<PetPhotoDomain> findPetPhotoByPetId(UUID id);
}
