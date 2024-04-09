package com.murilohenzo.petapi.adapters.outbound.persistence.impl;

import com.murilohenzo.petapi.adapters.mapper.PetMapper;
import com.murilohenzo.petapi.adapters.outbound.persistence.PetJpaRepository;
import com.murilohenzo.petapi.domain.models.PetDomain;
import com.murilohenzo.petapi.domain.models.enums.PetStatus;
import com.murilohenzo.petapi.domain.ports.PetPersistencePort;
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
  public List<PetDomain> findAll() {
    return repository.findAll().stream().map(petMapper::petEntityToPetDomain).toList();
  }

  @Override
  public List<PetDomain> findByStatus(PetStatus petStatus) {
    return repository.findByPetStatus(petStatus).stream().map(petMapper::petEntityToPetDomain).toList();
  }

  @Override
  public void deletePetByUserId(UUID userID) {
    repository.deletePetByUserId(userID);
  }

  @Override
  public List<PetDomain> findAllByUserId(UUID userID) {
    return repository.findAllByUser_Id(userID).stream().map(petMapper::petEntityToPetDomain).toList();
  }

}
