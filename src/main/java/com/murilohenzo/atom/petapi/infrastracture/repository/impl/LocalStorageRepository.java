package com.murilohenzo.atom.petapi.infrastracture.repository.impl;

import com.murilohenzo.atom.petapi.domain.repository.StorageRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@RequiredArgsConstructor
public class LocalStorageRepository implements StorageRepository {

  private final String uploadDirectory;

  @SneakyThrows
  public String saveImage(String fileName, InputStream is) {

    String imgReferenceId = UUID.randomUUID().toString().concat(fileName);
    Path uploadPath = Path.of(uploadDirectory);
    Path filePath = uploadPath.resolve(imgReferenceId);
    if (!Files.exists(uploadPath)) {
      Files.createDirectories(uploadPath);
    }
    Files.copy(is, filePath, StandardCopyOption.REPLACE_EXISTING);
    return imgReferenceId;
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
  public boolean deleteImage(String imgReferenceId) {
    Path imagePath = Path.of(uploadDirectory, imgReferenceId);
    if (Files.exists(imagePath)) {
      Files.delete(imagePath);
      return true;
    } else {
      return false;
    }
  }
} 
