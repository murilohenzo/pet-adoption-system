package com.murilo.algafoodapi.domain.service;

import com.murilo.algafoodapi.domain.entities.Pet;
import com.murilo.algafoodapi.infrastracture.repository.PetRepository;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class PetService {

    private final PetRepository repository;

    public Pet create(Pet pet) {
        return repository.save(pet);
    }
}
