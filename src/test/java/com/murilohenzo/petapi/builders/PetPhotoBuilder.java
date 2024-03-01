package com.murilohenzo.petapi.builders;

import com.murilohenzo.petapi.domain.entities.Pet;
import com.murilohenzo.petapi.domain.entities.PetPhoto;
import lombok.Builder;

@Builder
public class PetPhotoBuilder {
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
  private Pet pet;

  public PetPhoto petPhoto() {
    return PetPhoto.builder()
      .id(id)
      .name(name)
      .pet(PetBuilder.builder().build().pet())
      .build();
  }
}
