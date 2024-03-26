package com.murilohenzo.petapi.adapters.outbound.persistence.impl;

import com.murilohenzo.petapi.adapters.mapper.PetMapper;
import com.murilohenzo.petapi.adapters.outbound.persistence.PetPhotoJpaRepository;
import com.murilohenzo.petapi.domain.models.PetPhotoDomain;
import com.murilohenzo.petapi.domain.ports.PetPhotoPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class PetPhotoPersistencePortImpl implements PetPhotoPersistencePort {

  private final PetPhotoJpaRepository petPhotoRepository;
  private final PetMapper petMapper;

  @Override
  public PetPhotoDomain save(PetPhotoDomain photo) {
    return petMapper.petPhotoEntityToPetPhotoDomain(this.petPhotoRepository.save(petMapper.petPhotoDomainToPetPhotoEntity(photo)));
  }

  @Override
  public Optional<PetPhotoDomain> findPetPhotoByPetId(UUID id) {
    return this.petPhotoRepository.findById(id).map(petMapper::petPhotoEntityToPetPhotoDomain);
  }

}
