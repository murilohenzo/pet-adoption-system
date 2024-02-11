package com.murilohenzo.atom.petapi.adapters.rest;

import com.murilohenzo.atom.petapi.domain.mapper.PetMapper;
import com.murilohenzo.atom.petapi.domain.service.PetPhotoService;
import com.murilohenzo.atom.petapi.domain.service.PetService;
import com.murilohenzo.atom.petapi.presentation.PetApiDelegate;
import com.murilohenzo.atom.petapi.presentation.representation.PetPhotoResponseRepresentation;
import com.murilohenzo.atom.petapi.presentation.representation.PetRequestRepresentation;
import com.murilohenzo.atom.petapi.presentation.representation.PetResponseRepresentation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

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
  public ResponseEntity<PetResponseRepresentation> getPetById(Long petId) {
    var pet = petService.findById(petId);
    return pet.map(value -> ResponseEntity.ok(petMapper.toRepresentation(value)))
      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
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
  public ResponseEntity<PetPhotoResponseRepresentation> uploadFile(Long petId, MultipartFile image) {
    var pet = petService.findById(petId);

    if (pet.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    var photo = petPhotoService.create(pet.get(), image);
    var photosRepresentation = petMapper.toPetPhotosRepresentation(photo);
    return ResponseEntity.ok().body(photosRepresentation);
  }

  @Override
  public ResponseEntity<Resource> downloadFile(Long petId) {
    var photo = petPhotoService.getPhotoByPetId(petId);
    if (photo.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_TYPE, "image/jpg");

    ByteArrayResource resource = new ByteArrayResource(photo.get().image());

    return ResponseEntity.ok()
      .headers(headers)
      .contentType(photo.get().mediaType())
      .body(resource);
  }
}
