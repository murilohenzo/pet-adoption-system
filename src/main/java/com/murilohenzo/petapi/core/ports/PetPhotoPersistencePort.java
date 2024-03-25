package com.murilohenzo.petapi.core.ports;

import com.murilohenzo.petapi.core.domain.PetPhotoDomain;

import java.util.Optional;
import java.util.UUID;

public interface PetPhotoPersistencePort {
  PetPhotoDomain save(PetPhotoDomain photo);

  Optional<PetPhotoDomain> findPetPhotoByPetId(UUID id);
}
