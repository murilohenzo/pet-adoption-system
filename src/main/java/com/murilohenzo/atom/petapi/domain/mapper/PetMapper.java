package com.murilohenzo.atom.petapi.domain.mapper;

import org.mapstruct.Mapper;

import com.murilohenzo.atom.petapi.domain.entities.Pet;
import com.murilohenzo.atom.petapi.domain.entities.Status;
import com.murilohenzo.atom.petapi.presentation.representation.PetRequestRepresentation;
import com.murilohenzo.atom.petapi.presentation.representation.PetResponseRepresentation;
import com.murilohenzo.atom.petapi.presentation.representation.PetWithPhotosRepresentation;

@Mapper
public interface PetMapper {

  public Pet toEntityPet(PetRequestRepresentation petRepresentation);

  public PetResponseRepresentation toRepresentation(Pet pet);

  public PetWithPhotosRepresentation toPetWithPhotosRepresentation(Pet pet);

  public Status toStatus(String status);

}
