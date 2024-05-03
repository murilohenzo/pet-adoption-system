package com.murilohenzo.petapi.adapters.outbound.persistence.impl;

import com.murilohenzo.petapi.config.S3ClientConfigurationProperties;
import com.murilohenzo.petapi.domain.ports.StoragePersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RequiredArgsConstructor
@EnableConfigurationProperties(S3ClientConfigurationProperties.class)
public class S3StoragePersistencePortImpl implements StoragePersistencePort {
  
  private final S3Client s3Client;
  
  private final S3ClientConfigurationProperties s3Properties;
  
  @Override
  public String saveImage(String fileName, InputStream is) throws IOException {
    
    var referenceKey = generateId();

    PutObjectRequest request = PutObjectRequest.builder()
      .key(referenceKey)
      .bucket(s3Properties.getBucket())
      .build();
    
    s3Client.putObject(request, RequestBody.fromInputStream(is, is.available()));
    return referenceKey;
  }

  @Override
  public byte[] getImage(String referenceKey) {
    GetObjectRequest objectRequest = GetObjectRequest.builder()
      .key(referenceKey)
      .bucket(s3Properties.getBucket())
      .build();

    ResponseBytes<GetObjectResponse> responseBytes = s3Client.getObjectAsBytes(objectRequest);
    
    return responseBytes.asByteArray();
  }

  @Override
  public boolean deleteImage(String imgReferenceId) {
    return false;
  }
  
  private String generateId() {
    return UUID.randomUUID().toString();
  }
}
