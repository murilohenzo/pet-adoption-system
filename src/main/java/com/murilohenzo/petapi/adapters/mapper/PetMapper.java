package com.murilohenzo.petapi.adapters.mapper;

import com.murilohenzo.petapi.adapters.outbound.persistence.entities.PetEntity;
import com.murilohenzo.petapi.domain.models.PetDomain;
import com.murilohenzo.petapi.domain.models.enums.PetStatus;
import com.murilohenzo.petapi.presentation.representation.PetRequestRepresentation;
import com.murilohenzo.petapi.presentation.representation.PetResponseRepresentation;
import org.mapstruct.Mapper;

@Mapper
public interface PetMapper {

  // PetRepresentation
  public PetDomain petRequestRepresentationToPetDomain(PetRequestRepresentation petRepresentation);

  public PetResponseRepresentation petDomainToPetResponseRepresentation(PetDomain pet);

  public PetStatus toStatus(String status);

  // PetDomain  
  public PetEntity petDomainToPetEntity(PetDomain petDomain);

  public PetDomain petEntityToPetDomain(PetEntity petEntity);
}
