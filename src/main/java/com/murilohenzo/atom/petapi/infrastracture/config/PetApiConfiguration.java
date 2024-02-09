package com.murilohenzo.atom.petapi.infrastracture.config;

import com.murilohenzo.atom.petapi.adapters.rest.PetsApiDelegateImpl;
import com.murilohenzo.atom.petapi.domain.mapper.PetMapper;
import com.murilohenzo.atom.petapi.domain.repository.PetPhotoRepository;
import com.murilohenzo.atom.petapi.domain.repository.PetRepository;
import com.murilohenzo.atom.petapi.domain.service.PetPhotoService;
import com.murilohenzo.atom.petapi.domain.service.PetService;
import com.murilohenzo.atom.petapi.infrastracture.repository.SpringDataPetPhotoRepository;
import com.murilohenzo.atom.petapi.infrastracture.repository.SpringDataPetRepository;
import com.murilohenzo.atom.petapi.infrastracture.repository.impl.PetPhotoRepositoryImpl;
import com.murilohenzo.atom.petapi.infrastracture.repository.impl.PetRepositoryImpl;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PetApiConfiguration {

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
  public PetPhotoService petPhotoService(PetPhotoRepository petPhotoRepository) {
    return new PetPhotoService(petPhotoRepository);
  }

  @Bean
  public PetRepositoryImpl petRepository(SpringDataPetRepository petRepository) {
    return new PetRepositoryImpl(petRepository);
  }

  @Bean
  public PetPhotoRepositoryImpl petPhotoRepository(SpringDataPetPhotoRepository petPhotoRepository) {
    return new PetPhotoRepositoryImpl(petPhotoRepository);
  }

}
