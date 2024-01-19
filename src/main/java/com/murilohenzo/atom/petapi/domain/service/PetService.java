package com.murilohenzo.atom.petapi.domain.service;

import com.murilohenzo.atom.petapi.domain.entities.Pet;
import com.murilohenzo.atom.petapi.infrastracture.repository.PetRepository;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class PetService {

    private final PetRepository repository;

    public Pet create(Pet pet) {
        return repository.save(pet);
    }
}
