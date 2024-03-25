package com.murilohenzo.petapi.core.domain;

import com.murilohenzo.petapi.core.domain.enums.Gender;
import com.murilohenzo.petapi.core.domain.enums.Species;
import com.murilohenzo.petapi.core.domain.enums.Status;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class PetDomain {
  private UUID id;
  private String name;
  private String description;
  @ToString.Exclude
  private PetPhotoDomain petPhotoDomain;
  private Status status;
  private Gender gender;
  private Species species;
  private String breed;
  private Integer ageMoths;
  private LocalDate entryDate;
  private Instant createdAt;
  private Instant updatedAt;
}
