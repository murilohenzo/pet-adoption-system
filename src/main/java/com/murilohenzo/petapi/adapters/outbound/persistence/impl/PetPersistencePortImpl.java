package com.murilohenzo.petapi.adapters.outbound.persistence.impl;

import com.murilohenzo.petapi.adapters.mapper.PetMapper;
import com.murilohenzo.petapi.adapters.outbound.persistence.PetJpaRepository;
import com.murilohenzo.petapi.core.domain.PetDomain;
import com.murilohenzo.petapi.core.domain.enums.Status;
import com.murilohenzo.petapi.core.ports.PetPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class PetPersistencePortImpl implements PetPersistencePort {

  private final PetJpaRepository repository;
  private final PetMapper petMapper;

  @Override
  public PetDomain save(PetDomain pet) {
    var petEntity = repository.save(petMapper.petDomainToPetEntity(pet));
    return petMapper.petEntityToPetDomain(petEntity);
  }

  @Override
  public void update(PetDomain pet) {
    repository.save(petMapper.petDomainToPetEntity(pet));
  }

  @Override
  public void delete(UUID id) {
    repository.deleteById(id);
  }

  @Override
  public Optional<PetDomain> findById(UUID petId) {
    var petEntity = repository.findById(petId);
    return petEntity.map(petMapper::petEntityToPetDomain);
  }

  @Override
  public List<PetDomain> findByStatus(Status status) {
    return repository.findByStatus(status).stream().map(petMapper::petEntityToPetDomain).toList();
  }

}
