package com.murilohenzo.petapi.infrastracture.repository;

import com.murilohenzo.petapi.domain.entities.Pet;
import com.murilohenzo.petapi.domain.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataPetRepository extends JpaRepository<Pet, Long> {
  List<Pet> findByStatus(Status status);

}
