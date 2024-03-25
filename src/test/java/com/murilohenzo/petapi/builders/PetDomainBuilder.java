package com.murilohenzo.petapi.builders;

import com.murilohenzo.petapi.core.domain.PetDomain;
import com.murilohenzo.petapi.core.domain.enums.Gender;
import com.murilohenzo.petapi.core.domain.enums.Status;
import lombok.Builder;

import java.util.UUID;

@Builder
public class PetDomainBuilder {

  @Builder.Default
  private UUID id = UUID.fromString("00000000-0000-0000-0000-000000000001");

  @Builder.Default
  private String name = "Sunshine";

  @Builder.Default
  private String description = "Uma gatinha pequena e muito sapeca";

  @Builder.Default
  private Gender gender = Gender.FEMALE;

  @Builder.Default
  private Status status = Status.AVAILABLE;

  public PetDomain pet() {
    return PetDomain.builder()
      .id(id)
      .name(name)
      .description(description)
      .gender(gender)
      .status(status)
      .build();
  }
}
