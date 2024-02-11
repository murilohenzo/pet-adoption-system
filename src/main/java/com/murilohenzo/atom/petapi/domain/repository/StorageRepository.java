package com.murilohenzo.atom.petapi.domain.repository;

import java.io.InputStream;

public interface StorageRepository {
  public String saveImage(String fileName, InputStream is);

  public byte[] getImage(String imgReferenceId);

  public boolean deleteImage(String imgReferenceId);
}
