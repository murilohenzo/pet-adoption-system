package br.com.murilohenzo.ms.user.domain.mapper;

import br.com.murilohenzo.ms.user.domain.entities.User;
import br.com.murilohenzo.ms.user.presentation.representation.UserRequestRepresentation;
import br.com.murilohenzo.ms.user.presentation.representation.UserResponseRepresentation;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User toEntity(UserRequestRepresentation userRepresentation);
    UserResponseRepresentation toRepresentation(User user);
}
