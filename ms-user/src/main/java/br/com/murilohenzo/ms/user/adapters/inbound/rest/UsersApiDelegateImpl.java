package br.com.murilohenzo.ms.user.adapters.inbound.rest;

import br.com.murilohenzo.ms.user.presentation.UsersApiDelegate;
import br.com.murilohenzo.ms.user.presentation.representation.UserRepresentation;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public class UsersApiDelegateImpl implements UsersApiDelegate {

    @Override
    public ResponseEntity<List<UserRepresentation>> usersGet() {
        return UsersApiDelegate.super.usersGet();
    }

    @Override
    public ResponseEntity<Void> usersUserIdDelete(UUID userId) {
        return UsersApiDelegate.super.usersUserIdDelete(userId);
    }

    @Override
    public ResponseEntity<UserRepresentation> usersPost(UserRepresentation userRepresentation) {
        return UsersApiDelegate.super.usersPost(userRepresentation);
    }

    @Override
    public ResponseEntity<UserRepresentation> usersUserIdGet(UUID userId) {
        return UsersApiDelegate.super.usersUserIdGet(userId);
    }

    @Override
    public ResponseEntity<UserRepresentation> usersUserIdPut(UUID userId, UserRepresentation userRepresentation) {
        return UsersApiDelegate.super.usersUserIdPut(userId, userRepresentation);
    }
}
