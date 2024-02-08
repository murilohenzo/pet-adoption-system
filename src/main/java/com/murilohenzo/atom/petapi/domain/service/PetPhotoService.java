package com.murilohenzo.atom.petapi.domain.service;

import com.murilohenzo.atom.petapi.domain.entities.Pet;
import com.murilohenzo.atom.petapi.domain.entities.PetPhoto;
import com.murilohenzo.atom.petapi.domain.repository.PetPhotoRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
public class PetPhotoService {

    private final PetPhotoRepository petPhotoRepository;

    @Transactional
    @SneakyThrows
    public List<PetPhoto> create(Pet pet, List<MultipartFile> files) {

        // TODO: Adicionar logica de salva no S3 AWS e salvar no banco a referencia e url da imagem no S3

        return files.stream().map(f -> {
            PetPhoto petPhoto = new PetPhoto();
            petPhoto.setPet(pet);
            petPhoto.setSize((int) f.getSize());
            petPhoto.setPhotoUrl("aws-s3");
            petPhoto.setName(f.getName());
            petPhoto.setContentType(f.getContentType());
            return petPhoto;
        }).map(petPhotoRepository::save).toList();
    }

}
