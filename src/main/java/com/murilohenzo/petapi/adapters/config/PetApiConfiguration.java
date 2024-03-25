package com.murilohenzo.petapi.adapters.config;

import com.murilohenzo.petapi.adapters.inbound.rest.PetsApiDelegateImpl;
import com.murilohenzo.petapi.adapters.mapper.PetMapper;
import com.murilohenzo.petapi.adapters.outbound.persistence.PetJpaRepository;
import com.murilohenzo.petapi.adapters.outbound.persistence.PetPhotoJpaRepository;
import com.murilohenzo.petapi.adapters.outbound.persistence.impl.LocalStoragePersistencePortImpl;
import com.murilohenzo.petapi.adapters.outbound.persistence.impl.PetPersistencePortImpl;
import com.murilohenzo.petapi.adapters.outbound.persistence.impl.PetPhotoPersistencePortImpl;
import com.murilohenzo.petapi.adapters.utils.StorageUtils;
import com.murilohenzo.petapi.core.ports.PetPersistencePort;
import com.murilohenzo.petapi.core.ports.PetPhotoPersistencePort;
import com.murilohenzo.petapi.core.ports.StoragePersistencePort;
import com.murilohenzo.petapi.core.service.PetPhotoServicePortImpl;
import com.murilohenzo.petapi.core.service.PetServicePortImpl;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class PetApiConfiguration {

  @Bean
  PetsApiDelegateImpl petsApiDelegate(PetServicePortImpl petService, PetPhotoServicePortImpl petPhotoService, PetMapper petMapper) {
    return new PetsApiDelegateImpl(petService, petPhotoService, petMapper);
  }

  @Bean
  public PetMapper petMapper() {
    return Mappers.getMapper(PetMapper.class);
  }

  @Bean
  public PetServicePortImpl petService(PetPersistencePort petRepository) {
    return new PetServicePortImpl(petRepository);
  }

  @Bean
  public PetPhotoServicePortImpl petPhotoService(PetPhotoPersistencePort petPhotoRepository, StoragePersistencePort storageRepository) {
    return new PetPhotoServicePortImpl(petPhotoRepository, storageRepository);
  }

  @Bean
  public PetPersistencePortImpl petRepository(PetJpaRepository petRepository, PetMapper petMapper) {
    return new PetPersistencePortImpl(petRepository, petMapper);
  }

  @Bean
  public PetPhotoPersistencePortImpl petPhotoRepository(PetPhotoJpaRepository petPhotoRepository, PetMapper petMapper) {
    return new PetPhotoPersistencePortImpl(petPhotoRepository, petMapper);
  }

  @Bean
  @ConditionalOnProperty(name = "localstorage.enabled", havingValue = "true", matchIfMissing = false)
  public LocalStoragePersistencePortImpl localStorageRepository(@Value("${localstorage.path}") String localStoragePath) {
    log.info("[I63] - LOCALSTORAGE ENABLED DON'T USE IN PRODUCTION ENVIRONMENT");
    var dir = StorageUtils.mkdir(localStoragePath);
    return new LocalStoragePersistencePortImpl(dir);
  }
}
