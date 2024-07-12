package com.murilohenzo.petapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
  info = @Info(
    title = "PET API",
    version = "v1",
    description = "API for managing pets"
  ),
  security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
  name = "bearerAuth",
  type = SecuritySchemeType.HTTP,
  scheme = "bearer",
  bearerFormat = "JWT",
  in = SecuritySchemeIn.HEADER,
  paramName = "Authorization"
)
@SpringBootApplication
public class PetApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(PetApiApplication.class, args);
  }

}
