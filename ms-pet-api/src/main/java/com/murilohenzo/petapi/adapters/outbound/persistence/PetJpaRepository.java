package com.murilohenzo.petapi.adapters.outbound.persistence;

import com.murilohenzo.petapi.adapters.outbound.persistence.entities.PetEntity;
import com.murilohenzo.petapi.domain.models.enums.PetStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PetJpaRepository extends JpaRepository<PetEntity, UUID> {
  
  List<PetEntity> findByPetStatus(PetStatus petStatus);
  List<PetEntity> findAllByUser_Id(UUID userId);
  void deletePetByUserId(UUID userID);
  
}
