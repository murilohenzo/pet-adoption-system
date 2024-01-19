package com.murilo.algafoodapi.domain.mapper;

import com.murilo.algafoodapi.domain.entities.Pet;
import com.murilo.algafoodapi.presentation.representation.PetRepresentation;
import org.mapstruct.Mapper;

@Mapper
public interface PetMapper {
    public Pet toEntity(PetRepresentation petRepresentation);
    public PetRepresentation toRepresentation(Pet pet);
}
