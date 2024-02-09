package com.murilohenzo.atom.petapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "PET API", version = "v1", description = "API for managing pets"))
@SpringBootApplication
public class PetApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(PetApiApplication.class, args);
  }

}
