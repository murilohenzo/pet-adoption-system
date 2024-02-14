package com.murilohenzo.petapi.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.Objects;

@Entity(name = "tb_pet")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Pet {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @NotBlank
  @Column(nullable = false, length = 30)
  private String name;

  @Column(nullable = false, length = 250)
  private String description;

  @OneToOne(mappedBy = "pet", cascade = CascadeType.ALL)
  @ToString.Exclude
  private PetPhoto photo;

  @Enumerated(EnumType.STRING)
  private Status status = Status.PENDING;

  @Column
  private Gender gender;

  @Column(name = "created_at", updatable = false)
  private Instant createdAt;

  @Column(name = "updated_at")
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
    Pet pet = (Pet) o;
    return Objects.equals(id, pet.id) && Objects.equals(name, pet.name) && Objects.equals(description, pet.description) && Objects.equals(photo, pet.photo) && status == pet.status && gender == pet.gender && Objects.equals(createdAt, pet.createdAt) && Objects.equals(updatedAt, pet.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, photo, status, gender, createdAt, updatedAt);
  }
}
