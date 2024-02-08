package com.murilohenzo.atom.petapi.infrastracture.repository;

import com.murilohenzo.atom.petapi.domain.entities.PetPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataPetPhotoRepository extends JpaRepository<PetPhoto, Long> {
}
