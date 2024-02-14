package com.murilohenzo.petapi.domain.service;

import com.murilohenzo.petapi.domain.entities.Pet;
import com.murilohenzo.petapi.domain.entities.PetPhoto;
import com.murilohenzo.petapi.domain.repository.PetPhotoRepository;
import com.murilohenzo.petapi.domain.repository.StorageRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.http.entity.ContentType;
import org.apache.tomcat.util.http.fileupload.impl.InvalidContentTypeException;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class PetPhotoService {

  private final PetPhotoRepository petPhotoRepository;
  private final StorageRepository storageRepository;

  @Transactional
  @SneakyThrows
  public PetPhoto create(Pet pet, MultipartFile file) {
    if (file.isEmpty()) {
      throw new FileNotFoundException("Image file is empty");
    }

    contentTypeIsValid(file.getContentType());
    var referenceId = storageRepository.saveImage(file.getOriginalFilename(), file.getInputStream());

    var petPhoto = PetPhoto.builder()
      .pet(pet)
      .size(file.getSize())
      .photoUrl("aws-s3")
      .name(file.getName())
      .storageReferenceKey(referenceId)
      .contentType(file.getContentType())
      .build();
    return petPhotoRepository.save(petPhoto);
  }

  @Transactional(readOnly = true)
  @SneakyThrows
  public Optional<PetPhotoRecord> getPhotoByPetId(Long petId) {
    var petPhoto = this.petPhotoRepository.findPetPhotoByPetId(petId);
    if (petPhoto.isEmpty()) return Optional.empty();
    var image = storageRepository.getImage(petPhoto.get().getStorageReferenceKey());
    PetPhotoRecord photo = new PetPhotoRecord(image, MediaType.valueOf(petPhoto.get().getContentType()));
    return Optional.of(photo);
  }

  @SneakyThrows
  private static void contentTypeIsValid(String contentType) {
    List<ContentType> contentTypes = Arrays.asList(ContentType.IMAGE_JPEG, ContentType.IMAGE_PNG);
    if (!contentTypes.contains(ContentType.getByMimeType(contentType))) {
      throw new InvalidContentTypeException("ContentType invalid: " + contentType);
    }
  }
}
