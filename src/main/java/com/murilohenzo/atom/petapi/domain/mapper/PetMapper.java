package com.murilohenzo.atom.petapi.domain.mapper;

import com.murilohenzo.atom.petapi.domain.entities.Pet;
import com.murilohenzo.atom.petapi.presentation.representation.PetRepresentation;
import org.mapstruct.Mapper;

@Mapper
public interface PetMapper {
    public Pet toEntity(PetRepresentation petRepresentation);
    public PetRepresentation toRepresentation(Pet pet);
}
