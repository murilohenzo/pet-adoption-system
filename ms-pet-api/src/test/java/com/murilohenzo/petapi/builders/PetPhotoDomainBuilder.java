package com.murilohenzo.petapi.builders;

import com.murilohenzo.petapi.adapters.outbound.persistence.entities.PetEntity;
import com.murilohenzo.petapi.domain.models.PetPhotoDomain;
import lombok.Builder;

import java.util.UUID;

@Builder
public class PetPhotoDomainBuilder {
  
  @Builder.Default
  private Long id = 1L;

  @Builder.Default
  private String name = "image";

  @Builder.Default
  private Long size = 17803L;

  @Builder.Default
  private String contentType = "image/jpeg";

  @Builder.Default
  private String storageReferenceKey = "aws-s3";

  @Builder.Default
  private PetEntity pet;

  public PetPhotoDomain petPhoto() {
    return PetPhotoDomain.builder()
      .id(id)
      .name(name)
      .storageReferenceKey(storageReferenceKey)
      .contentType(contentType)
      .size(size)
      .pet(PetDomainBuilder.builder().build().pet())
      .build();
  }
}
