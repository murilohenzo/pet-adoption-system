package com.murilohenzo.petapi.adapters.outbound.persistence;

import com.murilohenzo.petapi.adapters.outbound.persistence.entities.UserRefEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRefJpaRepository extends JpaRepository<UserRefEntity, UUID> {
}
