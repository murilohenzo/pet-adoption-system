package com.murilohenzo.petapi.domain.models;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class PetPhotoDomain {
  private UUID id;
  private String name;
  private Long size;
  private String contentType;
  private String storageReferenceKey;
  @ToString.Exclude
  private PetDomain pet;
  private String photoUrl;
  private Instant createdAt;
  private Instant updatedAt;
}
