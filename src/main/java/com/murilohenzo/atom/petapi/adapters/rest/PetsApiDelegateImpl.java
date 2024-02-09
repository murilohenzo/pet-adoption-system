package com.murilohenzo.atom.petapi.adapters.rest;

import com.murilohenzo.atom.petapi.domain.mapper.PetMapper;
import com.murilohenzo.atom.petapi.domain.service.PetPhotoService;
import com.murilohenzo.atom.petapi.domain.service.PetService;
import com.murilohenzo.atom.petapi.presentation.PetApiDelegate;
import com.murilohenzo.atom.petapi.presentation.representation.PetPhotoResponseRepresentation;
import com.murilohenzo.atom.petapi.presentation.representation.PetRequestRepresentation;
import com.murilohenzo.atom.petapi.presentation.representation.PetResponseRepresentation;
import com.murilohenzo.atom.petapi.presentation.representation.PetWithPhotosRepresentation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
public class PetsApiDelegateImpl implements PetApiDelegate {

    private final PetService petService;
    private final PetPhotoService petPhotoService;
    private final PetMapper petMapper;

    @Override
    public ResponseEntity<PetResponseRepresentation> addPet(PetRequestRepresentation petRepresentation) {
        var pet = petMapper.toRepresentation(this.petService.create(petMapper.toEntityPet(petRepresentation)));
        return ResponseEntity.status(HttpStatus.CREATED).body(pet);
    }

    @Override
    public ResponseEntity<Void> deletePet(Long petId, String apiKey) {
        return PetApiDelegate.super.deletePet(petId, apiKey);
    }

    @Override
    public ResponseEntity<List<PetResponseRepresentation>> findPetsByStatus(String status) {
        var pets = petService.findPetsByStatus(petMapper.toStatus(status))
                .stream().map(petMapper::toRepresentation).toList();
        return ResponseEntity.ok(pets);
    }

    @Override
    public ResponseEntity<PetWithPhotosRepresentation> getPetById(Long petId) {
        var pet = petService.findById(petId);

        if (pet.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return pet.map(value -> ResponseEntity.ok(petMapper.toPetWithPhotosRepresentation(value))).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Override
    public ResponseEntity<PetResponseRepresentation> updatePet(PetResponseRepresentation petRepresentation) {
        return PetApiDelegate.super.updatePet(petRepresentation);
    }

    @Override
    public ResponseEntity<Void> updatePetWithForm(Long petId, String name, String status) {
        return PetApiDelegate.super.updatePetWithForm(petId, name, status);
    }

    @Override
    public ResponseEntity<List<PetPhotoResponseRepresentation>> uploadFile(Long petId, List<MultipartFile> file) {
        var pet = petService.findById(petId);

        if (pet.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        var photos = petPhotoService.create(pet.get(), file);

        var photosRepresentation = photos.stream().map(p -> {
            PetPhotoResponseRepresentation petPhotoResponseRepresentation = new PetPhotoResponseRepresentation();
            petPhotoResponseRepresentation.setId(p.getId());
            petPhotoResponseRepresentation.setPetId(p.getPet().getId());
            petPhotoResponseRepresentation.setCreatedAt(Date.from(p.getCreatedAt()));
            petPhotoResponseRepresentation.setPhotoUrl(p.getPhotoUrl());
            return petPhotoResponseRepresentation;
        }).toList();

        return ResponseEntity.ok().body(photosRepresentation);
    }
}