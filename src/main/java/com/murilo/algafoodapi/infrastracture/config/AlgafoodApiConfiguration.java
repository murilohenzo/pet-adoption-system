package com.murilo.algafoodapi.infrastracture.config;

import com.murilo.algafoodapi.adapters.rest.PetsApiDelegateImpl;
import com.murilo.algafoodapi.domain.mapper.PetMapper;
import com.murilo.algafoodapi.domain.service.PetService;
import com.murilo.algafoodapi.infrastracture.repository.PetRepository;
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
