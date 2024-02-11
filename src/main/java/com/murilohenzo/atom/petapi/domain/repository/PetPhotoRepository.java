package com.murilohenzo.atom.petapi.domain.repository;

import com.murilohenzo.atom.petapi.domain.entities.PetPhoto;

import java.util.Optional;

public interface PetPhotoRepository {
  PetPhoto save(PetPhoto photo);

  Optional<PetPhoto> findPetPhotoByPetId(Long id);
}
