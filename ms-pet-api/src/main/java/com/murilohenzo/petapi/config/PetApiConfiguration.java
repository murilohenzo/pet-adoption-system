package com.murilohenzo.petapi.config;

import com.murilohenzo.petapi.adapters.inbound.rest.PetsApiDelegateImpl;
import com.murilohenzo.petapi.adapters.mapper.PetMapper;
import com.murilohenzo.petapi.adapters.mapper.PetPhotoMapper;
import com.murilohenzo.petapi.adapters.mapper.UserMapper;
import com.murilohenzo.petapi.adapters.outbound.persistence.PetJpaRepository;
import com.murilohenzo.petapi.adapters.outbound.persistence.PetPhotoJpaRepository;
import com.murilohenzo.petapi.adapters.outbound.persistence.UserRefJpaRepository;
import com.murilohenzo.petapi.adapters.outbound.persistence.impl.LocalStoragePersistencePortImpl;
import com.murilohenzo.petapi.adapters.outbound.persistence.impl.PetPersistencePortImpl;
import com.murilohenzo.petapi.adapters.outbound.persistence.impl.PetPhotoPersistencePortImpl;
import com.murilohenzo.petapi.adapters.outbound.persistence.impl.UserPersistencePortImpl;
import com.murilohenzo.petapi.domain.ports.PetPersistencePort;
import com.murilohenzo.petapi.domain.ports.PetPhotoPersistencePort;
import com.murilohenzo.petapi.domain.ports.StoragePersistencePort;
import com.murilohenzo.petapi.domain.ports.UserPersistencePort;
import com.murilohenzo.petapi.domain.services.PetPhotoServicePortImpl;
import com.murilohenzo.petapi.domain.services.PetServicePortImpl;
import com.murilohenzo.petapi.utils.StorageUtils;
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
  PetsApiDelegateImpl petsApiDelegate(PetServicePortImpl petService,
                                      PetPhotoServicePortImpl petPhotoService,
                                      PetMapper petMapper,
                                      PetPhotoMapper petPhotoMapper) {
    return new PetsApiDelegateImpl(petService, petPhotoService, petMapper, petPhotoMapper);
  }

  @Bean
  public PetMapper petMapper() {
    return Mappers.getMapper(PetMapper.class);
  }
  
  @Bean
  public PetPhotoMapper petPhotoMapper() {
    return Mappers.getMapper(PetPhotoMapper.class);
  }
  
  @Bean
  public UserMapper userMapper() {
    return Mappers.getMapper(UserMapper.class);
  }

  @Bean
  public PetServicePortImpl petService(PetPersistencePort petPersistencePort, UserPersistencePort userPersistencePort) {
    return new PetServicePortImpl(petPersistencePort, userPersistencePort);
  }

  @Bean
  public PetPhotoServicePortImpl petPhotoService(PetPhotoPersistencePort petPhotoRepository, StoragePersistencePort storageRepository) {
    return new PetPhotoServicePortImpl(petPhotoRepository, storageRepository);
  }

  @Bean
  public PetPersistencePortImpl petRepository(PetJpaRepository petJpaRepository, PetMapper petMapper, UserMapper userMapper) {
    return new PetPersistencePortImpl(petJpaRepository, petMapper, userMapper);
  }

  @Bean
  public PetPhotoPersistencePortImpl petPhotoRepository(PetPhotoJpaRepository petPhotoJpaRepository, PetPhotoMapper petPhotoMapper) {
    return new PetPhotoPersistencePortImpl(petPhotoJpaRepository, petPhotoMapper);
  }
  
  @Bean
  public UserPersistencePortImpl userRefPersistencePort(UserRefJpaRepository userRefJpaRepository, UserMapper userMapper) {
    return new UserPersistencePortImpl(userRefJpaRepository, userMapper);
  }

  @Bean
  @ConditionalOnProperty(name = "localstorage.enabled", havingValue = "true", matchIfMissing = false)
  public LocalStoragePersistencePortImpl localStorageRepository(@Value("${localstorage.path}") String localStoragePath) {
    log.info("[I63] - LOCALSTORAGE ENABLED DON'T USE IN PRODUCTION ENVIRONMENT");
    var dir = StorageUtils.mkdir(localStoragePath);
    return new LocalStoragePersistencePortImpl(dir);
  }
}
