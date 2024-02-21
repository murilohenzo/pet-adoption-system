package com.murilohenzo.petapi.builders;

import com.murilohenzo.petapi.domain.entities.Gender;
import com.murilohenzo.petapi.presentation.representation.PetRequestRepresentation;
import lombok.Builder;

@Builder
public class PetRequestRepresentationBuilder {

  @Builder.Default
  private String name = "Sunshine";

  @Builder.Default
  private String description = "Uma gatinha pequena e muito sapeca";

  @Builder.Default
  private Gender gender = Gender.FEMALE;

  public PetRequestRepresentation petRequestRepresentation() {
    PetRequestRepresentation petRequestRepresentation = new PetRequestRepresentation();
    petRequestRepresentation.setName(name);
    petRequestRepresentation.setDescription(description);
    petRequestRepresentation.setGender(PetRequestRepresentation.GenderEnum.FEMALE);

    return petRequestRepresentation;
  }

}
