package com.murilohenzo.petapi.domain.service;

import org.springframework.http.MediaType;

public record PetPhotoRecord(byte[] image, MediaType mediaType) {
}
