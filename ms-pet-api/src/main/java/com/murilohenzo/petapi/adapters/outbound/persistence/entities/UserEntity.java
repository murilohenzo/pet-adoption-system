package com.murilohenzo.petapi.adapters.outbound.persistence.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity(name = "tb_user")
public class UserEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "username", nullable = false, unique = true)
  private String username;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "referenceId", unique = true)
  private String referenceId = UUID.randomUUID().toString();
  
  @Column(name = "created_at", updatable = false)
  private Instant createdAt;

  @Column(name = "updated_at", updatable = true)
  private Instant updatedAt;
  
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<PetEntity> pets;

  @PrePersist
  public void prePersist() {
    createdAt = Instant.now();
  }

  @PreUpdate
  public void preUpdate() {
    updatedAt = Instant.now();
  }
  
}
