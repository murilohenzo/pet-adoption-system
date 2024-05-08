package br.com.murilohenzo.ms.user;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "USER API", version = "v1", description = "API for managing users"))
@SpringBootApplication
public class MsUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsUserApplication.class, args);
	}

}
