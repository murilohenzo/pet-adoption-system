package com.murilohenzo.petapi.builders;

import com.murilohenzo.petapi.domain.models.PetDomain;
import com.murilohenzo.petapi.domain.models.enums.PetGender;
import com.murilohenzo.petapi.domain.models.enums.PetStatus;
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
  private PetGender petGender = PetGender.FEMALE;

  @Builder.Default
  private PetStatus petStatus = PetStatus.AVAILABLE;

  public PetDomain pet() {
    return PetDomain.builder()
      .id(id)
      .name(name)
      .description(description)
      .petGender(petGender)
      .petStatus(petStatus)
      .build();
  }
}
