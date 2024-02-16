package com.murilohenzo.petapi.adapters.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.murilohenzo.petapi.builders.PetBuilder;
import com.murilohenzo.petapi.domain.entities.Pet;
import com.murilohenzo.petapi.domain.mapper.PetMapper;
import com.murilohenzo.petapi.domain.mapper.PetMapperImpl;
import com.murilohenzo.petapi.domain.service.PetPhotoService;
import com.murilohenzo.petapi.domain.service.PetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@ExtendWith(MockitoExtension.class)
class PetsApiDelegateImplTest {

  private final static String PET_URL_PATH = "pet";

  @InjectMocks
  private PetsApiDelegateImpl petsApiDelegate;

  @Mock
  private PetService petService;

  @Mock
  private PetPhotoService petPhotoService;

  @Mock
  private PetMapper petMapper;

  private MockMvc mvc;

  private Pet pet;

  private ObjectMapper objectMapper;

  @BeforeEach
  public void setUp() {
    pet = PetBuilder.builder().build().pet();
    petMapper = new PetMapperImpl();
    objectMapper = new ObjectMapper();
    petsApiDelegate = new PetsApiDelegateImpl(petService, petPhotoService, petMapper);
    mvc = MockMvcBuilders.standaloneSetup(petsApiDelegate)
      .setControllerAdvice(new ErrorHandlerAdvice())
      .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
      .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
      .build();
  }

  @Test
  void givenValidPetRequest_whenAddPet_thenShouldReturnStatusCreated() {
  }

  @Test
  void givenInvalidPetRequest_whenAddPet_thenShouldThrowException() {
  }

  @Test
  void givenExistingPetId_whenDeletePet_thenShouldReturnStatusNoContent() {
  }

  @Test
  void givenNonExistingPetId_whenDeletePet_thenShouldThrowException() {
  }

  @Test
  void givenValidStatus_whenFindPetsByStatus_thenShouldReturnListOfPets() {
  }

  @Test
  void givenInvalidStatus_whenFindPetsByStatus_thenShouldThrowException() {
  }

  @Test
  void givenValidPetId_whenGetPetById_thenShouldReturnPet() {
  }

  @Test
  void givenInvalidPetId_whenGetPetById_thenShouldReturnNotFound() {
  }

  @Test
  void givenExistingPetIdAndValidPetRequest_whenUpdatePet_thenShouldReturnUpdatedPet() {
  }

  @Test
  void givenNonExistingPetIdAndValidPetRequest_whenUpdatePet_thenShouldThrowException() {
  }
  
  @Test
  void givenExistingPetIdAndValidImage_whenUploadFile_thenShouldReturnStatusOkWithPhoto() {
  }

  @Test
  void givenNonExistingPetIdOrInvalidImage_whenUploadFile_thenShouldThrowException() {
  }

  @Test
  void givenExistingPetId_whenDownloadFile_thenShouldReturnFile() {
  }

  @Test
  void givenNonExistingPetId_whenDownloadFile_thenShouldReturnNotFound() {
  }
}
