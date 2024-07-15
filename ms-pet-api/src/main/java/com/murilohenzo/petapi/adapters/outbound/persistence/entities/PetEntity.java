package com.murilohenzo.petapi.adapters.outbound.persistence.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.murilohenzo.petapi.domain.models.enums.PetGender;
import com.murilohenzo.petapi.domain.models.enums.PetSpecies;
import com.murilohenzo.petapi.domain.models.enums.PetStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity(name = "tb_pet")
@EntityListeners(AuditingEntityListener.class)
public class PetEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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

  @Column(name = "createdAt", columnDefinition = "TIMESTAMP")
  @CreatedDate
  private Instant createdAt;

  @Column(name = "updatedAt", columnDefinition = "TIMESTAMP")
  @LastModifiedDate
  private Instant updatedAt;

  @Column(name = "createdBy")
  @CreatedBy
  private String createdBy;

  @Column(name = "modifiedBy")
  @LastModifiedBy
  private String modifiedBy;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @JsonBackReference
  private UserEntity user;

  @Override
  public String toString() {
    return "PetEntity{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", description='" + description + '\'' +
      ", petStatus=" + petStatus +
      ", petGender=" + petGender +
      ", petSpecies=" + petSpecies +
      ", breed='" + breed + '\'' +
      ", ageMonths=" + ageMonths +
      ", entryDate=" + entryDate +
      ", createdAt=" + createdAt +
      ", updatedAt=" + updatedAt +
      ", createdBy='" + createdBy + '\'' +
      ", modifiedBy='" + modifiedBy + '\'' +
      '}';
  }
}
