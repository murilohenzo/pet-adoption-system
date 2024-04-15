package com.murilohenzo.petapi.adapters.mapper;

import com.murilohenzo.petapi.adapters.outbound.persistence.entities.PetPhotoEntity;
import com.murilohenzo.petapi.domain.models.PetPhotoDomain;
import com.murilohenzo.petapi.presentation.representation.PetPhotoResponseRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PetPhotoMapper {
  
  // PetPhotoRepresentation
  @Mapping(source = "pet.id", target = "petId")
  public PetPhotoResponseRepresentation petPhotoDomainToPetPhotosRepresentation(PetPhotoDomain petPhoto);
  
  // PetPhotoDomain
  public PetPhotoEntity petPhotoDomainToPetPhotoEntity(PetPhotoDomain petPhotoDomain);

  public PetPhotoDomain petPhotoEntityToPetPhotoDomain(PetPhotoEntity petPhotoEntity);
  
  
}
