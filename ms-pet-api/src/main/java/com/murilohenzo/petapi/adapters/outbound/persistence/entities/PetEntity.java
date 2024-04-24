package com.murilohenzo.petapi.adapters.outbound.persistence.entities;

import com.murilohenzo.petapi.domain.models.enums.PetGender;
import com.murilohenzo.petapi.domain.models.enums.PetSpecies;
import com.murilohenzo.petapi.domain.models.enums.PetStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.time.LocalDate;

@Data
@Entity(name = "tb_pet")
@NoArgsConstructor
public class PetEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false, length = 30)
  private String name;

  @Column(nullable = false, length = 250)
  private String description;

  @OneToOne(mappedBy = "pet", cascade = CascadeType.ALL)
  @ToString.Exclude
  private PetPhotoEntity photo;

  @Enumerated(EnumType.STRING)
  private PetStatus petStatus;

  @Column
  private PetGender petGender;

  @Enumerated(EnumType.STRING)
  private PetSpecies petSpecies;

  @Column(nullable = false, length = 30)
  private String breed;

  @Column(nullable = false)
  private Integer ageMonths;

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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private UserEntity user;

}
