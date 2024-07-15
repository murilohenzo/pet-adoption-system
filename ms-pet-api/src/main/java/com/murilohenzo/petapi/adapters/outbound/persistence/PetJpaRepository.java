package com.murilohenzo.petapi.adapters.outbound.persistence;

import com.murilohenzo.petapi.adapters.outbound.persistence.entities.PetEntity;
import com.murilohenzo.petapi.domain.models.enums.PetStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PetJpaRepository extends JpaRepository<PetEntity, Long> {
  
  List<PetEntity> findByPetStatus(PetStatus petStatus);
  List<PetEntity> findAllByUser_Id(Long userId);
  void deletePetByUserId(Long userID);
  void deletePetById(Long petId);

  @Modifying
  @Query("UPDATE com.murilohenzo.petapi.adapters.outbound.persistence.entities.PetEntity p SET p.petStatus = 'AVAILABLE', p.user = NULL WHERE p.user.id = :userId")
  void removeUserFromPets(@Param("userId") Long userId);
}
