package com.murilohenzo.petapi.adapters.outbound.persistence.impl;

import com.murilohenzo.petapi.adapters.mapper.PetMapper;
import com.murilohenzo.petapi.adapters.mapper.UserMapper;
import com.murilohenzo.petapi.adapters.outbound.persistence.UserRefJpaRepository;
import com.murilohenzo.petapi.adapters.outbound.persistence.entities.PetEntity;
import com.murilohenzo.petapi.adapters.outbound.persistence.entities.UserEntity;
import com.murilohenzo.petapi.domain.models.UserDomain;
import com.murilohenzo.petapi.domain.ports.UserPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
  public UserDomain save(UserDomain user) {
    return null;
  }

  @Override
  public void delete(Long userId) {
    userRefJpaRepository.deleteById(userId);
  }
}
