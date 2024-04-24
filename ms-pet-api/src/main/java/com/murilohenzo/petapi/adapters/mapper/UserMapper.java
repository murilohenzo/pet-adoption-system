package com.murilohenzo.petapi.adapters.mapper;

import com.murilohenzo.petapi.adapters.outbound.persistence.entities.UserEntity;
import com.murilohenzo.petapi.domain.models.UserDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {

  // PetDomain  
  @Mapping(source = "id", target = "id")
  public UserEntity userRefDomainToUserRefEntity(UserDomain userDomain);
  
  @Mapping(source = "id", target = "id")
  public UserDomain userRefEntityToUserRefDomain(UserEntity userEntity);
  
}
