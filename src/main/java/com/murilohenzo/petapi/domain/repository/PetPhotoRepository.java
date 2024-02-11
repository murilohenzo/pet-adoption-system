package com.murilohenzo.petapi.domain.repository;

import com.murilohenzo.petapi.domain.entities.PetPhoto;

import java.util.Optional;

public interface PetPhotoRepository {
  PetPhoto save(PetPhoto photo);

  Optional<PetPhoto> findPetPhotoByPetId(Long id);
}
