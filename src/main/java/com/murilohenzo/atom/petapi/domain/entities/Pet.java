package com.murilohenzo.atom.petapi.domain.entities;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "tb_pet")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Pet {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, length = 250)
  private String description;

  @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<PetPhoto> photos;

  @Enumerated(EnumType.STRING)
  private Status status = Status.PENDING;

  @Column
  private Gender gender;

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
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Pet pet = (Pet) o;
    return Objects.equals(id, pet.id) && Objects.equals(name, pet.name) && Objects.equals(photos, pet.photos)
        && status == pet.status && Objects.equals(createdAt, pet.createdAt) && Objects.equals(updatedAt, pet.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, photos, status, createdAt, updatedAt);
  }
}
