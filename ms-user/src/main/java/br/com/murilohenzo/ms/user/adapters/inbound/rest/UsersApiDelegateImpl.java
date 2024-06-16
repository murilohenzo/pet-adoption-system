package br.com.murilohenzo.ms.user.adapters.inbound.rest;

import br.com.murilohenzo.ms.user.domain.mapper.UserMapper;
import br.com.murilohenzo.ms.user.domain.services.UserService;
import br.com.murilohenzo.ms.user.presentation.UsersApiDelegate;
import br.com.murilohenzo.ms.user.presentation.representation.UserRequestRepresentation;
import br.com.murilohenzo.ms.user.presentation.representation.UserResponseRepresentation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RequiredArgsConstructor
public class UsersApiDelegateImpl implements UsersApiDelegate {

    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    public ResponseEntity<List<UserResponseRepresentation>> usersGet() {
        return ResponseEntity.ok(userService.findAll().stream().map(userMapper::toRepresentation).toList());
    }

    @Override
    public ResponseEntity<UserResponseRepresentation> usersPost(UserRequestRepresentation userRequestRepresentation) {
        var user = userService.createNewUser(userMapper.toEntity(userRequestRepresentation));
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toRepresentation(user));
    }

    @Override
    public ResponseEntity<Void> usersUserIdDelete(Long userId) {
        var user = userService.findById(userId);
        userService.deleteUser(user);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<UserResponseRepresentation> usersUserIdGet(Long userId) {
        return ResponseEntity.ok(userMapper.toRepresentation(userService.findById(userId)));
    }

    @Override
    public ResponseEntity<UserResponseRepresentation> usersUserIdPut(Long userId, UserRequestRepresentation userRequestRepresentation) {
        var user = userService.updateUser(userId, userMapper.toEntity(userRequestRepresentation));
        return ResponseEntity.ok(userMapper.toRepresentation(user));
    }
}
