package com.murilohenzo.atom.petapi.infrastracture.config;

import com.murilohenzo.atom.petapi.adapters.rest.PetsApiDelegateImpl;
import com.murilohenzo.atom.petapi.domain.mapper.PetMapper;
import com.murilohenzo.atom.petapi.domain.service.PetService;
import com.murilohenzo.atom.petapi.infrastracture.repository.PetRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlgafoodApiConfiguration {

    @Bean
    PetsApiDelegateImpl petsApiDelegate(PetService petService, PetMapper petMapper) {
        return new PetsApiDelegateImpl(petService, petMapper);
    }

    @Bean
    public PetMapper petMapper() {
        return Mappers.getMapper(PetMapper.class);
    }

    @Bean
    public PetService petService(PetRepository petRepository) {
        return new PetService(petRepository);
    }
}
