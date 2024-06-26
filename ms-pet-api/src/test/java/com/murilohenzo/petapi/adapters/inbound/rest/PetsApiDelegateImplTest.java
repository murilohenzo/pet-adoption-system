package com.murilohenzo.petapi.adapters.inbound.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.murilohenzo.petapi.adapters.mapper.PetMapper;
import com.murilohenzo.petapi.adapters.mapper.PetPhotoMapper;
import com.murilohenzo.petapi.builders.PetDomainBuilder;
import com.murilohenzo.petapi.builders.PetPhotoDomainBuilder;
import com.murilohenzo.petapi.builders.PetRequestRepresentationBuilder;
import com.murilohenzo.petapi.domain.models.PetDomain;
import com.murilohenzo.petapi.domain.models.PetPhotoDomain;
import com.murilohenzo.petapi.domain.models.enums.PetStatus;
import com.murilohenzo.petapi.domain.services.PetPhotoServicePortImpl;
import com.murilohenzo.petapi.domain.services.PetServicePortImpl;
import com.murilohenzo.petapi.presentation.PetsApiController;
import com.murilohenzo.petapi.presentation.representation.PetRequestRepresentation;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)

class PetsApiDelegateImplTest {

  private final static String PET_URL_PATH = "/pets";

  @InjectMocks
  private PetsApiController petsApiController;

  @InjectMocks
  private PetsApiDelegateImpl petsApiDelegate;

  @Mock
  private PetServicePortImpl petService;

  @Mock
  private PetPhotoServicePortImpl petPhotoService;

  @Mock
  private PetMapper petMapper;
  
  @Mock
  private PetPhotoMapper petPhotoMapper;

  private MockMvc mvc;

  private PetDomain pet;

  private PetPhotoDomain petPhoto;
  
  private ObjectMapper objectMapper;

  private PetRequestRepresentation petRequestRepresentation;

  
  @BeforeEach
  public void setUp() {
    objectMapper = new ObjectMapper();

    pet = PetDomainBuilder.builder().build().pet();
    petRequestRepresentation = PetRequestRepresentationBuilder.builder().build().petRequestRepresentation();
    petPhoto = PetPhotoDomainBuilder.builder().build().petPhoto();
    
    petMapper = Mappers.getMapper(PetMapper.class);
    petPhotoMapper = Mappers.getMapper(PetPhotoMapper.class);
    petsApiDelegate = new PetsApiDelegateImpl(petService, petPhotoService, petMapper, petPhotoMapper);
    petsApiController = new PetsApiController(petsApiDelegate);

    mvc = MockMvcBuilders.standaloneSetup(petsApiController)
      .setControllerAdvice(new ErrorHandlerAdvice())
      .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
      .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
      .build();
  }

  @Test
  void givenValidPetRequest_whenAddPet_thenShouldReturnStatusCreated() throws Exception {
    var bodyAsObject = petMapper.petRequestRepresentationToPetDomain(petRequestRepresentation);
    var bodyAsJSON = objectMapper.writeValueAsString(petRequestRepresentation);

    when(petService.create(bodyAsObject)).thenReturn(pet);

    mvc.perform(post(PET_URL_PATH)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(bodyAsJSON))
      .andDo(print())
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id", is(pet.getId().intValue())));
  }

  @Test
  void givenInvalidPetRequest_whenAddPet_thenShouldThrowException() throws Exception {
    petRequestRepresentation.setName(null);
    petRequestRepresentation.setGender(null);
    petRequestRepresentation.setDescription(null);

    var bodyAsJSON = objectMapper.writeValueAsString(petRequestRepresentation);

    mvc.perform(post(PET_URL_PATH)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(bodyAsJSON))
      .andDo(print())
      .andExpect(status().isBadRequest());
  }

  @Test
  void givenExistingPetId_whenDeletePet_thenShouldReturnStatusNoContent() throws Exception {
    var validId = 1L;

    doNothing().when(petService).delete(validId);

    mvc.perform(delete(PET_URL_PATH.concat("/").concat(String.valueOf(validId)))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isNoContent());
  }

  @Test
  void givenNonExistingPetId_whenDeletePet_thenShouldThrowException() throws Exception {
    var invalidId = 100L;

    doThrow(EntityNotFoundException.class).when(petService).delete(invalidId);

    mvc.perform(delete(PET_URL_PATH.concat("/").concat(String.valueOf(invalidId)))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isNotFound());
  }

  @Test
  void givenValidStatus_whenFindPetsByStatus_thenShouldReturnListOfPets() throws Exception {
    when(petService.findPetsByStatus(PetStatus.AVAILABLE)).thenReturn(List.of(pet));

    mvc.perform(get(PET_URL_PATH.concat("/").concat("findByStatus"))
        .queryParam("status", PetStatus.AVAILABLE.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").isArray())
      .andExpect(jsonPath("$.length()").value(1));
  }

  @Test
  void givenValidPetId_whenGetPetById_thenShouldReturnPet() throws Exception {
    var validId = 1L;

    when(petService.findById(validId)).thenReturn(Optional.of(pet));

    mvc.perform(get(PET_URL_PATH.concat("/").concat(String.valueOf(validId)))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id", is(pet.getId().intValue())))
      .andExpect(jsonPath("$.status", is(pet.getPetStatus().name())));
  }

  @Test
  void givenInvalidPetId_whenGetPetById_thenShouldReturnNotFound() throws Exception {
    var invalidId = 100L;

    when(petService.findById(invalidId)).thenReturn(Optional.empty());

    mvc.perform(get(PET_URL_PATH.concat("/").concat(String.valueOf(invalidId)))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isNotFound());
  }

  @Test
  void givenExistingPetIdAndValidPetRequest_whenUpdatePet_thenShouldReturnUpdatedPet() throws Exception {
    var validId = 1L;
    var bodyAsObject = petMapper.petRequestRepresentationToPetDomain(petRequestRepresentation);
    var bodyAsJSON = objectMapper.writeValueAsString(petRequestRepresentation);

    when(petService.update(validId, bodyAsObject)).thenReturn(pet);

    mvc.perform(put(PET_URL_PATH.concat("/").concat(String.valueOf(validId)))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(bodyAsJSON))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id", is(pet.getId().intValue())))
      .andExpect(jsonPath("$.status", is(pet.getPetStatus().name())));
  }

  @Test
  void givenNonExistingPetIdAndValidPetRequest_whenUpdatePet_thenShouldThrowException() throws Exception {
    var invalidId = 100L;
    var bodyAsObject = petMapper.petRequestRepresentationToPetDomain(petRequestRepresentation);
    var bodyAsJSON = objectMapper.writeValueAsString(petRequestRepresentation);

    doThrow(EntityNotFoundException.class).when(petService).update(invalidId, bodyAsObject);

    mvc.perform(put(PET_URL_PATH.concat("/").concat(String.valueOf(invalidId)))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(bodyAsJSON))
      .andDo(print())
      .andExpect(status().isNotFound());
  }
  
  @Test
  void givenExistingPetIdAndValidImage_whenUploadFile_thenShouldReturnStatusOkWithPhoto() throws Exception {
    var validId = 1L;

    Resource fileResource = new ClassPathResource("static/images/teste.jpg");

    MockMultipartFile image = new MockMultipartFile(
      "image", fileResource.getFilename(),
      MediaType.IMAGE_JPEG_VALUE,
      fileResource.getInputStream());

    when(petService.findById(validId)).thenReturn(Optional.of(pet));
    when(petPhotoService.create(pet, image)).thenReturn(petPhoto);

    mvc.perform(multipart(PET_URL_PATH.concat("/").concat(String.valueOf(validId).concat("/photo")))
        .file(image)
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .accept(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id", is(pet.getId().intValue())))
      .andExpect(jsonPath("$.contentType", is(petPhoto.getContentType())));
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
