package com.murilohenzo.petapi.core.ports;

import java.io.InputStream;

public interface StoragePersistencePort {
  public String saveImage(String fileName, InputStream is);

  public byte[] getImage(String imgReferenceId);

  public boolean deleteImage(String imgReferenceId);
}
