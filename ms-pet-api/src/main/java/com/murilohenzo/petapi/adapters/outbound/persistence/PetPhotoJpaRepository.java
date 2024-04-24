package com.murilohenzo.petapi.adapters.outbound.persistence;

import com.murilohenzo.petapi.adapters.outbound.persistence.entities.PetPhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PetPhotoJpaRepository extends JpaRepository<PetPhotoEntity, Long> {
  Optional<PetPhotoEntity> findPhotoByPetId(Long petId);
}
