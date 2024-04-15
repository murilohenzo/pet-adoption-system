package com.murilohenzo.petapi;

import com.murilohenzo.petapi.utils.StorageUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles({"local", "test"})
class PetApiApplicationTest {

  @AfterAll
  static void tearDown(@Value("${localstorage.path}") String localStoragePath) {
    StorageUtils.rmdir(localStoragePath);
  }

  @Test
  void contextLoads() {
  }
}
