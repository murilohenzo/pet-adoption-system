package com.murilohenzo.petapi.domain.models;

import com.murilohenzo.petapi.domain.models.enums.Gender;
import com.murilohenzo.petapi.domain.models.enums.Species;
import com.murilohenzo.petapi.domain.models.enums.Status;
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
