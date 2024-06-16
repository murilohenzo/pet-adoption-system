package com.murilohenzo.petapi.domain.models;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Set;

@Data
@Builder
public class UserDomain {
  private Long id;
  private String username;
  private String email;
  private String referenceId;
  private Instant createdAt;
  private Instant updatedAt;
  private Set<PetDomain> pets;
}
