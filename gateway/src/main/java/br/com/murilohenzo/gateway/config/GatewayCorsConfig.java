package br.com.murilohenzo.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@EnableWebFlux
public class GatewayCorsConfig implements WebFluxConfigurer {

  private static final Logger log = LoggerFactory.getLogger(GatewayCorsConfig.class);

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    log.info("Configuring CORS for Spring Cloud Gateway");

    registry.addMapping("/**")
            .allowedOrigins("http://localhost:4200")
            .allowedMethods("*")
            .allowedHeaders("*")
            .exposedHeaders("*")
            .allowCredentials(true);
  }
}
