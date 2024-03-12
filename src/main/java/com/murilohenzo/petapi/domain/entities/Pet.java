package com.murilohenzo.petapi.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

@Entity(name = "tb_pet")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pet {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 30)
  private String name;

  @Column(nullable = false, length = 250)
  private String description;

  @OneToOne(mappedBy = "pet", cascade = CascadeType.ALL)
  @ToString.Exclude
  private PetPhoto photo;

  @Enumerated(EnumType.STRING)
  private Status status;

  @Column
  private Gender gender;

  @Enumerated(EnumType.STRING)
  private Species species;

  @Column(nullable = false, length = 30)
  private String breed;

  @Column(nullable = false)
  private Integer ageMoths;

  @Column(nullable = false)
  private LocalDate entryDate;

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
