package com.murilohenzo.petapi.domain.models;

import com.murilohenzo.petapi.domain.models.enums.PetGender;
import com.murilohenzo.petapi.domain.models.enums.PetSpecies;
import com.murilohenzo.petapi.domain.models.enums.PetStatus;
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
  private PetStatus petStatus;
  private PetGender petGender;
  private PetSpecies petSpecies;
  private String breed;
  private Integer ageMonths;
  private LocalDate entryDate;
  @ToString.Exclude
  private UserRefDomain user;
  private Instant createdAt;
  private Instant updatedAt;
}
