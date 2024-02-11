package com.murilohenzo.petapi.infrastracture.repository.impl;

import com.murilohenzo.petapi.domain.entities.PetPhoto;
import com.murilohenzo.petapi.domain.repository.PetPhotoRepository;
import com.murilohenzo.petapi.infrastracture.repository.SpringDataPetPhotoRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class PetPhotoRepositoryImpl implements PetPhotoRepository {

  private final SpringDataPetPhotoRepository petPhotoRepository;

  @Override
  public PetPhoto save(PetPhoto photo) {
    return this.petPhotoRepository.save(photo);
  }

  @Override
  public Optional<PetPhoto> findPetPhotoByPetId(Long id) {
    return this.petPhotoRepository.findPetPhotoByPetId(id);
  }

}
