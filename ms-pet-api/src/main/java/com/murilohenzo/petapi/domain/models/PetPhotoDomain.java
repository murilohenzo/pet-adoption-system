package com.murilohenzo.petapi.domain.models;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.Instant;

@Data
@Builder
public class PetPhotoDomain {
  private Long id;
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
