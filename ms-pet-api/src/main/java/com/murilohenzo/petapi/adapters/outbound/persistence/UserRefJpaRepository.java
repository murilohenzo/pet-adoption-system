package com.murilohenzo.petapi.adapters.outbound.persistence;

import com.murilohenzo.petapi.adapters.outbound.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRefJpaRepository extends JpaRepository<UserEntity, Long> {
  void deleteByReferenceId(String referenceId);
  Optional<UserEntity> findByReferenceId(String referenceId);
}
