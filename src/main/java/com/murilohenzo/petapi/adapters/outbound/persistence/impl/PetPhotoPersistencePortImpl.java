package com.murilohenzo.petapi.adapters.outbound.persistence.impl;

import com.murilohenzo.petapi.adapters.mapper.PetPhotoMapper;
import com.murilohenzo.petapi.adapters.outbound.persistence.PetPhotoJpaRepository;
import com.murilohenzo.petapi.domain.models.PetPhotoDomain;
import com.murilohenzo.petapi.domain.ports.PetPhotoPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class PetPhotoPersistencePortImpl implements PetPhotoPersistencePort {

  private final PetPhotoJpaRepository petPhotoRepository;
  private final PetPhotoMapper petPhotoMapper;

  @Override
  public PetPhotoDomain save(PetPhotoDomain photo) {
    return petPhotoMapper.petPhotoEntityToPetPhotoDomain(this.petPhotoRepository.save(petPhotoMapper.petPhotoDomainToPetPhotoEntity(photo)));
  }

  @Override
  public Optional<PetPhotoDomain> findPetPhotoByPetId(UUID id) {
    return petPhotoRepository.findPhotoByPetId(id).map(petPhotoMapper::petPhotoEntityToPetPhotoDomain);
  }

}
