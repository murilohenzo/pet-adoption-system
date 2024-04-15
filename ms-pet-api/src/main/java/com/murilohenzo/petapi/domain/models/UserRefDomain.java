package com.murilohenzo.petapi.domain.models;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class UserRefDomain {
  private UUID userId;
  private String email;
  private String fullName;
  private String userStatus;
  private String userType;
  private String cpf;
  private Instant createdAt;
  private Instant updatedAt;
  private Set<PetDomain> courses;
}
