package com.murilohenzo.petapi.domain.models;

import org.springframework.http.MediaType;

public record PetPhotoRecord(byte[] image, MediaType mediaType) {
}
