package com.murilohenzo.petapi.adapters.outbound.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_user")
public class UserRefEntity {
  
  @Id
  private UUID id;
  
  @Column( nullable = false, unique = true, length = 50)
  private String email;
  
  @Column(nullable = false, length = 150)
  private String fullName;
  
  @Column(nullable=false)
  private String userStatus;
  
  @Column(nullable=false)
  private String userType;
  
  @Column(length = 20)
  private String cpf;

  @Column(name = "created_at", updatable = false)
  private Instant createdAt;

  @Column(name = "updated_at", updatable = true)
  private Instant updatedAt;
  
  @ManyToMany(mappedBy = "user", fetch = FetchType.LAZY)
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
