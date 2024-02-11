package com.murilohenzo.atom.petapi.infrastracture.repository.impl;

import com.murilohenzo.atom.petapi.domain.entities.PetPhoto;
import com.murilohenzo.atom.petapi.domain.repository.PetPhotoRepository;
import com.murilohenzo.atom.petapi.infrastracture.repository.SpringDataPetPhotoRepository;
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
