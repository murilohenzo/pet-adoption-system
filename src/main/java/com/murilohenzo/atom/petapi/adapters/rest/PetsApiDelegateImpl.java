package com.murilohenzo.atom.petapi.adapters.rest;

import com.murilohenzo.atom.petapi.domain.mapper.PetMapper;
import com.murilohenzo.atom.petapi.domain.service.PetService;
import com.murilohenzo.atom.petapi.presentation.PetApiDelegate;
import com.murilohenzo.atom.petapi.presentation.representation.ApiResponseRepresentation;
import com.murilohenzo.atom.petapi.presentation.representation.PetRepresentation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class PetsApiDelegateImpl implements PetApiDelegate {

    private final PetService petService;
    private final PetMapper petMapper;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return PetApiDelegate.super.getRequest();
    }

    @Override
    public ResponseEntity<PetRepresentation> addPet(PetRepresentation petRepresentation) {
        var pet = petMapper.toRepresentation(this.petService.create(petMapper.toEntity(petRepresentation)));
        return ResponseEntity.status(HttpStatus.CREATED).body(pet);
    }

    @Override
    public ResponseEntity<Void> deletePet(Long petId, String apiKey) {
        return PetApiDelegate.super.deletePet(petId, apiKey);
    }

    @Override
    public ResponseEntity<List<PetRepresentation>> findPetsByStatus(String status) {
        return PetApiDelegate.super.findPetsByStatus(status);
    }

    @Override
    public ResponseEntity<PetRepresentation> getPetById(Long petId) {
        return PetApiDelegate.super.getPetById(petId);
    }

    @Override
    public ResponseEntity<PetRepresentation> updatePet(PetRepresentation petRepresentation) {
        return PetApiDelegate.super.updatePet(petRepresentation);
    }

    @Override
    public ResponseEntity<Void> updatePetWithForm(Long petId, String name, String status) {
        return PetApiDelegate.super.updatePetWithForm(petId, name, status);
    }

    @Override
    public ResponseEntity<ApiResponseRepresentation> uploadFile(Long petId, String additionalMetadata, MultipartFile file) {
        return PetApiDelegate.super.uploadFile(petId, additionalMetadata, file);
    }
}
