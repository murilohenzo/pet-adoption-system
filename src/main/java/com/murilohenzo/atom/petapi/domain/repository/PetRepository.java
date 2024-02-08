package com.murilohenzo.atom.petapi.domain.repository;

import com.murilohenzo.atom.petapi.domain.entities.Pet;
import com.murilohenzo.atom.petapi.domain.entities.Status;

import java.util.List;
import java.util.Optional;

public interface PetRepository {

    List<Pet> findByStatus(Status status);

    Optional<Pet> findById(Long id);

    Pet save(Pet pet);

    void update(Pet pet);

    void delete(Long id);
}
