package com.murilohenzo.atom.petapi.domain.mapper;

import com.murilohenzo.atom.petapi.domain.entities.Pet;
import com.murilohenzo.atom.petapi.domain.entities.Status;
import com.murilohenzo.atom.petapi.presentation.representation.PetRequestRepresentation;
import com.murilohenzo.atom.petapi.presentation.representation.PetResponseRepresentation;
import org.mapstruct.Mapper;

@Mapper
public interface PetMapper {

    public Pet toEntityPet(PetRequestRepresentation petRepresentation);

    public PetResponseRepresentation toRepresentation(Pet pet);

    public Status toStatus(String status);

}
