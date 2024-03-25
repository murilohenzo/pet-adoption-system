package com.murilohenzo.petapi.core.domain;

import org.springframework.http.MediaType;

public record PetPhotoRecord(byte[] image, MediaType mediaType) {
}
