package com.murilohenzo.petapi.builders;

import com.murilohenzo.petapi.domain.entities.Gender;
import com.murilohenzo.petapi.domain.entities.Pet;
import com.murilohenzo.petapi.domain.entities.Status;
import lombok.Builder;

@Builder
public class PetBuilder {

  @Builder.Default
  private Long id = 1L;

  @Builder.Default
  private String name = "Sunshine";

  @Builder.Default
  private String description = "Uma gatinha pequena e muito sapeca";

  @Builder.Default
  private Gender gender = Gender.FEMALE;

  @Builder.Default
  private Status status = Status.AVAILABLE;

  public Pet pet() {
    return Pet.builder()
      .id(id)
      .name(name)
      .description(description)
      .gender(gender)
      .status(status)
      .build();
  }
}
