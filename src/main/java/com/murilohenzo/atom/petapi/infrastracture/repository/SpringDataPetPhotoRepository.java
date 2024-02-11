package com.murilohenzo.atom.petapi.infrastracture.repository;

import com.murilohenzo.atom.petapi.domain.entities.PetPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataPetPhotoRepository extends JpaRepository<PetPhoto, Long> {

  Optional<PetPhoto> findPetPhotoByPetId(Long id);

}
