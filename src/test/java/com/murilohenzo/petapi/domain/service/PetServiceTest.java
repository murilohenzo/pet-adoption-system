package com.murilohenzo.petapi.domain.service;

import com.murilohenzo.petapi.builders.PetBuilder;
import com.murilohenzo.petapi.domain.entities.Status;
import com.murilohenzo.petapi.domain.repository.PetRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

  @Mock
  private PetRepository petRepository;

  @InjectMocks
  private PetService petService;

  @Test
  void givenValidPet_whenCreatePet_thenPetIsCreated() {
    // Given
    var validPet = PetBuilder.builder().build().pet();
    // When
    when(petRepository.save(validPet)).thenReturn(validPet);
    var pet = petService.create(validPet);
    // Then
    assertNotNull(pet);
    assertEquals(validPet.getName(), pet.getName());
    assertEquals(Status.AVAILABLE, pet.getStatus());
    assertEquals(validPet.getDescription(), pet.getDescription());
  }

  @Test
  void givenStatus_whenFindPetsByStatus_thenListOfPetsIsReturned() {
    // Given
    var expectedPets = List.of(PetBuilder.builder().build().pet());
    // When
    when(petRepository.findByStatus(Status.AVAILABLE)).thenReturn(expectedPets);
    var pets = petService.findPetsByStatus(Status.AVAILABLE);
    // Then
    assertNotNull(pets);
    assertFalse(pets.isEmpty());
    assertEquals(expectedPets, pets);
  }

  @Test
  void givenPetId_whenFindById_thenPetIsReturned() {
    // Given
    var validId = 1L;
    var expectedPet = PetBuilder.builder().build().pet();
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
    var expectedPet = PetBuilder.builder().build().pet();
    // When
    when(petRepository.findById(validId)).thenReturn(Optional.of(expectedPet));
    doNothing().when(petRepository).delete(validId);
    petService.delete(validId);
    // Then
    verify(petRepository, times(1)).findById(validId);
    verify(petRepository, times(1)).delete(validId);
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
    var existPet = PetBuilder.builder().build().pet();
    var expectedPet = PetBuilder.builder().build().pet();
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
    var expectedPet = PetBuilder.builder().build().pet();
    // When
    when(petRepository.findById(invalidId)).thenReturn(Optional.empty());
    // Then
    assertThrows(EntityNotFoundException.class, () -> petService.update(invalidId, expectedPet));
  }

}
