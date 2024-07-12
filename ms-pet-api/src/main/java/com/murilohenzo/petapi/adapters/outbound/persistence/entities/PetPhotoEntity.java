package com.murilohenzo.petapi.adapters.outbound.persistence.entities;

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

@Data
@Entity(name = "tb_pet_photo")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PetPhotoEntity {

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
  private PetEntity pet;

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
}
