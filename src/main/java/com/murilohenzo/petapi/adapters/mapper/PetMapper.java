package com.murilohenzo.petapi.adapters.mapper;

import com.murilohenzo.petapi.adapters.outbound.persistence.entities.PetEntity;
import com.murilohenzo.petapi.adapters.outbound.persistence.entities.PetPhotoEntity;
import com.murilohenzo.petapi.domain.models.PetDomain;
import com.murilohenzo.petapi.domain.models.PetPhotoDomain;
import com.murilohenzo.petapi.domain.models.enums.Status;
import com.murilohenzo.petapi.presentation.representation.PetPhotoResponseRepresentation;
import com.murilohenzo.petapi.presentation.representation.PetRequestRepresentation;
import com.murilohenzo.petapi.presentation.representation.PetResponseRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PetMapper {

  public PetDomain petRequestRepresentationToPetDomain(PetRequestRepresentation petRepresentation);

  public PetResponseRepresentation petDomainToPetResponseRepresentation(PetDomain pet);
  @Mapping(source = "pet.id", target = "petId")
  public PetPhotoResponseRepresentation petPhotoDomainToPetPhotosRepresentation(PetPhotoDomain petPhoto);
  public Status toStatus(String status);

  // PetDomain  
  public PetEntity petDomainToPetEntity(PetDomain petDomain);

  public PetDomain petEntityToPetDomain(PetEntity petEntity);

  // PetPhotoDomain
  public PetPhotoEntity petPhotoDomainToPetPhotoEntity(PetPhotoDomain petPhotoDomain);

  public PetPhotoDomain petPhotoEntityToPetPhotoDomain(PetPhotoEntity petPhotoEntity);
}
