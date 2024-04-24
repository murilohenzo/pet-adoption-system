package com.murilohenzo.petapi.adapters.outbound.persistence.impl;

import com.murilohenzo.petapi.adapters.mapper.PetMapper;
import com.murilohenzo.petapi.adapters.mapper.UserMapper;
import com.murilohenzo.petapi.adapters.outbound.persistence.PetJpaRepository;
import com.murilohenzo.petapi.domain.models.PetDomain;
import com.murilohenzo.petapi.domain.models.enums.PetStatus;
import com.murilohenzo.petapi.domain.ports.PetPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class PetPersistencePortImpl implements PetPersistencePort {

  private final PetJpaRepository repository;
  private final PetMapper petMapper;
  private final UserMapper userMapper;

  @Override
  public PetDomain save(PetDomain pet) {
    var petEntity = repository.save(petMapper.petDomainToPetEntity(pet));
    return petMapper.petEntityToPetDomain(petEntity);
  }

  @Override
  public void saveUserPet(PetDomain petDomain) {
    var user = petDomain.getUser();
    if (user != null) {
      var userEntity = userMapper.userRefDomainToUserRefEntity(user);
      var petEntity = petMapper.petDomainToPetEntity(petDomain);
      petEntity.setUser(userEntity);
      repository.save(petEntity);
    }
  }

  @Override
  public void update(PetDomain pet) {
    repository.save(petMapper.petDomainToPetEntity(pet));
  }

  @Override
  public void deletePetById(Long id) {
    repository.deletePetById(id);
  }

  @Override
  public Optional<PetDomain> findById(Long petId) {
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
  public void deletePetByUserId(Long userID) {
    repository.deletePetByUserId(userID);
  }

  @Override
  public List<PetDomain> findAllByUserId(Long userID) {
    return repository.findAllByUser_Id(userID).stream().map(petMapper::petEntityToPetDomain).toList();
  }

}
