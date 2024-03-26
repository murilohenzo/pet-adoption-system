package com.murilohenzo.petapi.builders;

import com.murilohenzo.petapi.presentation.representation.PetRequestRepresentation;
import lombok.Builder;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

@Builder
public class PetRequestRepresentationBuilder {

  @Builder.Default
  private String name = "Sunshine";

  @Builder.Default
  private String description = "Uma gatinha pequena e muito sapeca";

  @Builder.Default
  private String breed = "Persa";
  
  public PetRequestRepresentation petRequestRepresentation() {

    Instant instantForTest = Instant.parse("2024-01-11T12:00:00Z");

    Clock clock = Clock.fixed(instantForTest, ZoneId.systemDefault());
    
    PetRequestRepresentation petRequestRepresentation = new PetRequestRepresentation();
    petRequestRepresentation.setName(name);
    petRequestRepresentation.setDescription(description);
    petRequestRepresentation.setSpecies(PetRequestRepresentation.SpeciesEnum.CAT);
    petRequestRepresentation.setBreed(breed);
    petRequestRepresentation.setEntryDate(Date.from(clock.instant()));
    petRequestRepresentation.setAgeMoths(2);
    petRequestRepresentation.setGender(PetRequestRepresentation.GenderEnum.FEMALE);

    return petRequestRepresentation;
  }

}
