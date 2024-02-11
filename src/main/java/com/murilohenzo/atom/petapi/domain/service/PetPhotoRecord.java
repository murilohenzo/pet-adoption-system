package com.murilohenzo.atom.petapi.domain.service;

import org.springframework.http.MediaType;

public record PetPhotoRecord(byte[] image, MediaType mediaType) {
}
