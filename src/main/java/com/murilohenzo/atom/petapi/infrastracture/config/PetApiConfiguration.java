package com.murilohenzo.atom.petapi.infrastracture.config;

import com.murilohenzo.atom.petapi.adapters.rest.PetsApiDelegateImpl;
import com.murilohenzo.atom.petapi.domain.mapper.PetMapper;
import com.murilohenzo.atom.petapi.domain.repository.PetPhotoRepository;
import com.murilohenzo.atom.petapi.domain.repository.PetRepository;
import com.murilohenzo.atom.petapi.domain.repository.StorageRepository;
import com.murilohenzo.atom.petapi.domain.service.PetPhotoService;
import com.murilohenzo.atom.petapi.domain.service.PetService;
import com.murilohenzo.atom.petapi.infrastracture.repository.SpringDataPetPhotoRepository;
import com.murilohenzo.atom.petapi.infrastracture.repository.SpringDataPetRepository;
import com.murilohenzo.atom.petapi.infrastracture.repository.impl.LocalStorageRepository;
import com.murilohenzo.atom.petapi.infrastracture.repository.impl.PetPhotoRepositoryImpl;
import com.murilohenzo.atom.petapi.infrastracture.repository.impl.PetRepositoryImpl;
import com.murilohenzo.atom.petapi.utils.StorageUtils;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class PetApiConfiguration {

  @Value("${localstorage.path}")
  private String localStoragePath;

  @Bean
  PetsApiDelegateImpl petsApiDelegate(PetService petService, PetPhotoService petPhotoService, PetMapper petMapper) {
    return new PetsApiDelegateImpl(petService, petPhotoService, petMapper);
  }

  @Bean
  public PetMapper petMapper() {
    return Mappers.getMapper(PetMapper.class);
  }

  @Bean
  public PetService petService(PetRepository petRepository) {
    return new PetService(petRepository);
  }

  @Bean
  public PetPhotoService petPhotoService(PetPhotoRepository petPhotoRepository, StorageRepository storageRepository) {
    return new PetPhotoService(petPhotoRepository, storageRepository);
  }

  @Bean
  public PetRepositoryImpl petRepository(SpringDataPetRepository petRepository) {
    return new PetRepositoryImpl(petRepository);
  }

  @Bean
  public PetPhotoRepositoryImpl petPhotoRepository(SpringDataPetPhotoRepository petPhotoRepository) {
    return new PetPhotoRepositoryImpl(petPhotoRepository);
  }

  @Bean
  @ConditionalOnProperty(name = "localstorage.enabled", havingValue = "true", matchIfMissing = false)
  public LocalStorageRepository localStorageRepository() {
    log.info("[I63] - LOCALSTORAGE ENABLED DON'T USE IN PRODUCTION ENVIRONMENT");
    var dir = StorageUtils.mkdir(localStoragePath);
    return new LocalStorageRepository(dir);
  }
}
