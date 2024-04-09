package com.murilohenzo.petapi.adapters.outbound.persistence;

import com.murilohenzo.petapi.adapters.outbound.persistence.entities.PetPhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PetPhotoJpaRepository extends JpaRepository<PetPhotoEntity, UUID> {
  Optional<PetPhotoEntity> findPhotoByPetId(UUID petId);
}
