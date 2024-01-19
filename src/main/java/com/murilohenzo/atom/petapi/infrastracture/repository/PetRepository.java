package com.murilohenzo.atom.petapi.infrastracture.repository;

import com.murilohenzo.atom.petapi.domain.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
}
