package com.murilohenzo.petapi.adapters.outbound.persistence.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity(name = "tb_user")
public class UserEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "username", nullable = false, unique = true)
  private String username;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "referenceId", unique = true)
  private String referenceId = UUID.randomUUID().toString();
  
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<PetEntity> pets;

  @Column(name = "createdAt", columnDefinition = "TIMESTAMP")
  @CreatedDate
  private Instant createdAt;

  @Column(name = "updatedAt", columnDefinition = "TIMESTAMP")
  @LastModifiedDate
  private Instant updatedAt;
}
