package com.murilohenzo.petapi.domain.ports;

import java.io.IOException;
import java.io.InputStream;

public interface StoragePersistencePort {
  
  public String saveImage(String fileName, InputStream is) throws IOException;
  public byte[] getImage(String referenceKey);
  public void deleteImage(String referenceKey);
  
}
