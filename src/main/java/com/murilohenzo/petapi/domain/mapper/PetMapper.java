package com.murilohenzo.petapi.domain.mapper;

import com.murilohenzo.petapi.domain.entities.Pet;
import com.murilohenzo.petapi.domain.entities.PetPhoto;
import com.murilohenzo.petapi.domain.entities.Status;
import com.murilohenzo.petapi.presentation.representation.PetPhotoResponseRepresentation;
import com.murilohenzo.petapi.presentation.representation.PetRequestRepresentation;
import com.murilohenzo.petapi.presentation.representation.PetResponseRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PetMapper {

  public Pet toEntityPet(PetRequestRepresentation petRepresentation);
  public PetResponseRepresentation toRepresentation(Pet pet);

  @Mapping(source = "pet.id", target = "petId")
  public PetPhotoResponseRepresentation toPetPhotosRepresentation(PetPhoto petPhoto);
  public Status toStatus(String status);

}
