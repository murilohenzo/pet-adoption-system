package com.murilohenzo.petapi.adapters.outbound.persistence.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity(name = "tb_user")
public class UserEntity {
  
  @Id
  private Long id;

  @Column(length = 20, unique = true)
  private String cpf;
  
  @Column(name = "user_status", nullable=false)
  private String userStatus;
  
  @Column(name = "user_type", nullable=false)
  private String userType;
  
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
