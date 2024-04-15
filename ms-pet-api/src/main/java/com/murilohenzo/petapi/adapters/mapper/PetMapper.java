package com.murilohenzo.petapi.adapters.mapper;

import com.murilohenzo.petapi.adapters.outbound.persistence.entities.PetEntity;
import com.murilohenzo.petapi.domain.models.PetDomain;
import com.murilohenzo.petapi.domain.models.enums.PetStatus;
import com.murilohenzo.petapi.presentation.representation.PetRequestRepresentation;
import com.murilohenzo.petapi.presentation.representation.PetResponseRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PetMapper {

  // PetRepresentation
  @Mapping(source = "gender", target = "petGender")
  @Mapping(source = "species", target = "petSpecies")
  public PetDomain petRequestRepresentationToPetDomain(PetRequestRepresentation petRepresentation);

  @Mapping(source = "petStatus", target = "status")
  @Mapping(source = "petGender", target = "gender")
  @Mapping(source = "petSpecies", target = "species")
  public PetResponseRepresentation petDomainToPetResponseRepresentation(PetDomain pet);

  public PetStatus toStatus(String status);

  // PetDomain  
  public PetEntity petDomainToPetEntity(PetDomain petDomain);

  public PetDomain petEntityToPetDomain(PetEntity petEntity);
}
