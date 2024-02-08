package com.murilohenzo.atom.petapi.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.util.Objects;

@Entity(name = "tb_pet_photo")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class PetPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Integer size;

    @Column
    private String contentType;

    @Column
    private String storageReferenceKey;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @Column(nullable = false)
    private String photoUrl;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PetPhoto petPhoto = (PetPhoto) o;
        return Objects.equals(id, petPhoto.id) && Objects.equals(name, petPhoto.name) && Objects.equals(size, petPhoto.size) && Objects.equals(storageReferenceKey, petPhoto.storageReferenceKey) && Objects.equals(pet, petPhoto.pet) && Objects.equals(photoUrl, petPhoto.photoUrl) && Objects.equals(createdAt, petPhoto.createdAt) && Objects.equals(updatedAt, petPhoto.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, size, storageReferenceKey, pet, photoUrl, createdAt, updatedAt);
    }
}
