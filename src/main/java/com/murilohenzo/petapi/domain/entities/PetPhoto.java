package com.murilohenzo.petapi.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Objects;

@Entity(name = "tb_pet_photo")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetPhoto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private Long size;

  @Column(nullable = false)
  private String contentType;

  @Column(nullable = false)
  private String storageReferenceKey;

  @OneToOne
  @JoinColumn(name = "pet_id")
  @ToString.Exclude
  private Pet pet;

  @Column(nullable = false)
  private String photoUrl;

  @Column(name = "created_at", updatable = false)
  private Instant createdAt;

  @Column(name = "updated_at", updatable = true)
  private Instant updatedAt;

  @PrePersist
  public void prePersist() {
    createdAt = Instant.now();
  }

  @PreUpdate
  public void preUpdate() {
    updatedAt = Instant.now();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PetPhoto petPhoto = (PetPhoto) o;
    return Objects.equals(id, petPhoto.id) && Objects.equals(name, petPhoto.name) && Objects.equals(size, petPhoto.size) && Objects.equals(contentType, petPhoto.contentType) && Objects.equals(storageReferenceKey, petPhoto.storageReferenceKey) && Objects.equals(pet, petPhoto.pet) && Objects.equals(photoUrl, petPhoto.photoUrl) && Objects.equals(createdAt, petPhoto.createdAt) && Objects.equals(updatedAt, petPhoto.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, size, contentType, storageReferenceKey, pet, photoUrl, createdAt, updatedAt);
  }
}
