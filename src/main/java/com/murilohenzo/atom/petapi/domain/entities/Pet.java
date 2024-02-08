package com.murilohenzo.atom.petapi.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.util.List;
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

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<PetPhoto> photos;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return Objects.equals(id, pet.id) && Objects.equals(name, pet.name) && Objects.equals(photos, pet.photos) && status == pet.status && Objects.equals(createdAt, pet.createdAt) && Objects.equals(updatedAt, pet.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, photos, status, createdAt, updatedAt);
    }
}
