package com.murilohenzo.petapi.core.service;

import com.murilohenzo.petapi.core.domain.PetDomain;
import com.murilohenzo.petapi.core.domain.PetPhotoDomain;
import com.murilohenzo.petapi.core.domain.PetPhotoRecord;
import com.murilohenzo.petapi.core.ports.PetPhotoPersistencePort;
import com.murilohenzo.petapi.core.ports.StoragePersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.http.entity.ContentType;
import org.apache.tomcat.util.http.fileupload.impl.InvalidContentTypeException;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class PetPhotoServicePortImpl {

  private final PetPhotoPersistencePort petPhotoRepository;
  private final StoragePersistencePort storageRepository;

  @Transactional
  @SneakyThrows
  public PetPhotoDomain create(PetDomain pet, MultipartFile file) {
    if (file.isEmpty()) {
      throw new FileNotFoundException("Image file is empty");
    }

    contentTypeIsValid(file.getContentType());
    var referenceId = storageRepository.saveImage(file.getOriginalFilename(), file.getInputStream());

    var petPhoto = PetPhotoDomain.builder()
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
  public Optional<PetPhotoRecord> getPhotoByPetId(UUID petId) {
    var petPhoto = this.petPhotoRepository.findPetPhotoByPetId(petId);
    if (petPhoto.isEmpty()) return Optional.empty();
    var image = storageRepository.getImage(petPhoto.get().getStorageReferenceKey());
    PetPhotoRecord photo = new PetPhotoRecord(image, MediaType.valueOf(petPhoto.get().getContentType()));
    return Optional.of(photo);
  }

  @SneakyThrows
  private static void contentTypeIsValid(String contentType) {
    var contentTypes = List.of(ContentType.IMAGE_JPEG, ContentType.IMAGE_PNG);
    if (!contentTypes.contains(ContentType.getByMimeType(contentType))) {
      throw new InvalidContentTypeException("ContentType invalid: " + contentType);
    }
  }
}
