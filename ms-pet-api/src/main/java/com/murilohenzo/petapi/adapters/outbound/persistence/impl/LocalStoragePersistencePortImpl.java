package com.murilohenzo.petapi.adapters.outbound.persistence.impl;

import com.murilohenzo.petapi.domain.ports.StoragePersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@RequiredArgsConstructor
public class LocalStoragePersistencePortImpl implements StoragePersistencePort {

  private final String uploadDirectory;

  @SneakyThrows
  public String saveImage(String fileName, InputStream is) {

    String referenceKey = UUID.randomUUID().toString().concat(fileName);
    Path uploadPath = Path.of(uploadDirectory);
    Path filePath = uploadPath.resolve(referenceKey);
    if (!Files.exists(uploadPath)) {
      Files.createDirectories(uploadPath);
    }
    Files.copy(is, filePath, StandardCopyOption.REPLACE_EXISTING);
    return referenceKey;
  }

  @SneakyThrows
  public byte[] getImage(String imgReferenceId) {
    Path imagePath = Path.of(uploadDirectory, imgReferenceId);
    if (Files.exists(imagePath)) {
      return Files.readAllBytes(imagePath);
    } else {
      return new byte[0];
    }
  }

  @SneakyThrows
  public void deleteImage(String imgReferenceId) {
    Path imagePath = Path.of(uploadDirectory, imgReferenceId);
    if (Files.exists(imagePath)) {
      Files.delete(imagePath);
    }
  }
} 
