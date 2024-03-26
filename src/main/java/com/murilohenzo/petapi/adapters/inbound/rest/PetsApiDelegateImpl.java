package com.murilohenzo.petapi.adapters.inbound.rest;

import com.murilohenzo.petapi.adapters.mapper.PetMapper;
import com.murilohenzo.petapi.domain.services.PetPhotoServicePortImpl;
import com.murilohenzo.petapi.domain.services.PetServicePortImpl;
import com.murilohenzo.petapi.presentation.PetsApiDelegate;
import com.murilohenzo.petapi.presentation.representation.PetPhotoResponseRepresentation;
import com.murilohenzo.petapi.presentation.representation.PetRequestRepresentation;
import com.murilohenzo.petapi.presentation.representation.PetResponseRepresentation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class PetsApiDelegateImpl implements PetsApiDelegate {

  private final PetServicePortImpl petService;
  private final PetPhotoServicePortImpl petPhotoService;
  private final PetMapper petMapper;

  @Override
  public ResponseEntity<PetResponseRepresentation> addPet(PetRequestRepresentation petRepresentation) {
    var pet = petMapper.petDomainToPetResponseRepresentation(petService.create(petMapper.petRequestRepresentationToPetDomain(petRepresentation)));
    return ResponseEntity.status(HttpStatus.CREATED).body(pet);
  }

  @Override
  public ResponseEntity<Void> deletePet(UUID petId) {
    petService.delete(petId);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<List<PetResponseRepresentation>> findPetsByStatus(String status) {
    var pets = petService.findPetsByStatus(petMapper.toStatus(status.toUpperCase()))
      .stream().map(petMapper::petDomainToPetResponseRepresentation).toList();
    return ResponseEntity.ok(pets);
  }

  @Override
  public ResponseEntity<PetResponseRepresentation> getPetById(UUID petId) {
    var pet = petService.findById(petId);
    return pet.map(value -> ResponseEntity.ok(petMapper.petDomainToPetResponseRepresentation(value)))
      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @Override
  public ResponseEntity<PetResponseRepresentation> updatePet(UUID petId, PetRequestRepresentation petRequestRepresentation) {
    var pet = petService.update(petId, petMapper.petRequestRepresentationToPetDomain(petRequestRepresentation));
    return ResponseEntity.ok(petMapper.petDomainToPetResponseRepresentation(pet));
  }

  @Override
  public ResponseEntity<PetPhotoResponseRepresentation> uploadFile(UUID petId, MultipartFile image) {
    var pet = petService.findById(petId);

    if (pet.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    var photo = petPhotoService.create(pet.get(), image);
    var photosRepresentation = petMapper.petPhotoDomainToPetPhotosRepresentation(photo);
    return ResponseEntity.ok().body(photosRepresentation);
  }

  @Override
  public ResponseEntity<Resource> downloadFile(UUID petId) {
    var photo = petPhotoService.getPhotoByPetId(petId);
    if (photo.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_TYPE, photo.get().mediaType().toString());

    ByteArrayResource resource = new ByteArrayResource(photo.get().image());

    return ResponseEntity.ok()
      .headers(headers)
      .contentType(photo.get().mediaType())
      .body(resource);
  }
}
