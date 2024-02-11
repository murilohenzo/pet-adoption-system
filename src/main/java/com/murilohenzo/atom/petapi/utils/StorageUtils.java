package com.murilohenzo.atom.petapi.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class StorageUtils {

  private static final File CURRENT_DIRECTORY = new File(System.getProperty("user.dir"));
  private static final File TEMP_DIRECTORY = new File(System.getProperty("java.io.tmpdir"));

  private StorageUtils() {
  }

  @SneakyThrows
  public static String mkdir(String uploadDirectory) {
    File dir = new File(CURRENT_DIRECTORY, uploadDirectory);
    log.info("[I19] - Create directory:{}", dir.getAbsolutePath());
    dir.mkdir();
    return dir.getAbsolutePath();
  }
}
