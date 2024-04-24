package com.murilohenzo.petapi.domain.models.service;

import com.murilohenzo.petapi.builders.PetDomainBuilder;
import com.murilohenzo.petapi.domain.models.enums.PetStatus;
import com.murilohenzo.petapi.domain.ports.PetPersistencePort;
import com.murilohenzo.petapi.domain.services.PetServicePortImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

  @Mock
  private PetPersistencePort petRepository;

  @InjectMocks
  private PetServicePortImpl petService;

  @Test
  void givenValidPet_whenCreatePet_thenPetIsCreated() {
    // Given
    var validPet = PetDomainBuilder.builder().build().pet();
    // When
    when(petRepository.save(validPet)).thenReturn(validPet);
    var pet = petService.create(validPet);
    // Then
    assertNotNull(pet);
    assertEquals(validPet.getName(), pet.getName());
    assertEquals(PetStatus.AVAILABLE, pet.getPetStatus());
    assertEquals(validPet.getDescription(), pet.getDescription());
  }

  @Test
  void givenStatus_whenFindPetsByStatus_thenListOfPetsIsReturned() {
    // Given
    var expectedPets = List.of(PetDomainBuilder.builder().build().pet());
    // When
    when(petRepository.findByStatus(PetStatus.AVAILABLE)).thenReturn(expectedPets);
    var pets = petService.findPetsByStatus(PetStatus.AVAILABLE);
    // Then
    assertNotNull(pets);
    assertFalse(pets.isEmpty());
    assertEquals(expectedPets, pets);
  }

  @Test
  void givenPetId_whenFindById_thenPetIsReturned() {
    // Given
    var validId = 1L;
    var expectedPet = PetDomainBuilder.builder().build().pet();
    // When
    when(petRepository.findById(validId)).thenReturn(Optional.ofNullable(expectedPet));
    var pet = petService.findById(validId);
    // Then
    assertNotNull(pet.get());
    assertFalse(pet.isEmpty());
    assertEquals(validId, pet.get().getId());
    assertEquals(expectedPet, pet.get());
  }

  @Test
  void givenExistingPetId_whenDelete_thenPetIsDeleted() {
    // Given
    var validId = 1L;
    var expectedPet = PetDomainBuilder.builder().build().pet();
    // When
    when(petRepository.findById(validId)).thenReturn(Optional.of(expectedPet));
    doNothing().when(petRepository).deletePetById(validId);
    petService.delete(validId);
    // Then
    verify(petRepository, times(1)).findById(validId);
    verify(petRepository, times(1)).deletePetById(validId);
  }

  @Test
  void givenNonExistingPetId_whenDelete_thenThrowException() {
    // Given
    var invalidId = 100L;
    // When
    when(petRepository.findById(invalidId)).thenReturn(Optional.empty());
    // Then
    assertThrows(EntityNotFoundException.class, () -> petService.delete(invalidId));
  }

  @Test
  void givenExistingPetIdAndUpdatedPet_whenUpdate_thenPetIsUpdated() {
    // Given
    var validId = 1L;
    var existPet = PetDomainBuilder.builder().build().pet();
    var expectedPet = PetDomainBuilder.builder().build().pet();
    expectedPet.setDescription("Gato laranja");
    // When
    when(petRepository.findById(validId)).thenReturn(Optional.of(existPet));
    when(petRepository.save(expectedPet)).thenReturn(expectedPet);
    var pet = petService.update(validId, expectedPet);
    // Then
    assertNotNull(pet);
    assertEquals(existPet.getDescription(), expectedPet.getDescription());
    assertEquals("Gato laranja", existPet.getDescription());
    assertEquals("Gato laranja", expectedPet.getDescription());
  }

  @Test
  void givenNonExistingPetIdAndUpdatedPet_whenUpdate_thenThrowException() {
    // Given
    var invalidId = 100L;
    var expectedPet = PetDomainBuilder.builder().build().pet();
    // When
    when(petRepository.findById(invalidId)).thenReturn(Optional.empty());
    // Then
    assertThrows(EntityNotFoundException.class, () -> petService.update(invalidId, expectedPet));
  }

}
