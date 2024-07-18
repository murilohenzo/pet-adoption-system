package com.murilohenzo.petapi.adapters.outbound.persistence.impl;

import com.murilohenzo.petapi.adapters.mapper.UserMapper;
import com.murilohenzo.petapi.adapters.outbound.persistence.UserRefJpaRepository;
import com.murilohenzo.petapi.domain.models.UserDomain;
import com.murilohenzo.petapi.domain.ports.UserPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserPersistencePortImpl implements UserPersistencePort {
  
  private final UserRefJpaRepository userRefJpaRepository;
  private final UserMapper userMapper;

  @Override
  public Optional<UserDomain> findById(Long userId) {
    var userRef = userRefJpaRepository.findById(userId);
    return userRef.map(userMapper::userRefEntityToUserRefDomain);
  }

  @Override
  public Optional<UserDomain> findByReferenceId(String referenceId) {
    var userRef = userRefJpaRepository.findByReferenceId(referenceId);
    return userRef.map(userMapper::userRefEntityToUserRefDomain);
  }

  @Override
  public void save(UserDomain user) {
    userRefJpaRepository.save(userMapper.userRefDomainToUserRefEntity(user)); 
  }

  @Override
  public void delete(String referenceId) {
    userRefJpaRepository.deleteByReferenceId(referenceId);
  }
}
