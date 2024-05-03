package com.murilohenzo.petapi.config;

import com.murilohenzo.petapi.adapters.outbound.persistence.impl.LocalStoragePersistencePortImpl;
import com.murilohenzo.petapi.adapters.outbound.persistence.impl.S3StoragePersistencePortImpl;
import com.murilohenzo.petapi.utils.StorageUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;
import software.amazon.awssdk.services.s3.S3Configuration;

@Slf4j
@Configuration
@EnableConfigurationProperties(S3ClientConfigurationProperties.class)
public class StorageConfiguration {

  @Bean
  @ConditionalOnProperty(name = "localstorage.enabled", havingValue = "true", matchIfMissing = false)
  public LocalStoragePersistencePortImpl localStorageRepository(@Value("${localstorage.path}") String localStoragePath) {
    log.info("[I29] - LOCALSTORAGE ENABLED DON'T USE IN PRODUCTION ENVIRONMENT");
    var dir = StorageUtils.mkdir(localStoragePath);
    return new LocalStoragePersistencePortImpl(dir);
  }

  @Bean
  @ConditionalOnProperty(name = "aws.s3.enabled", havingValue = "true", matchIfMissing = false)
  public S3Client s3Client(S3ClientConfigurationProperties s3props, AwsCredentialsProvider credentialsProvider) {
    S3Configuration s3Configuration = S3Configuration.builder()
      .checksumValidationEnabled(false)
      .chunkedEncodingEnabled(true)
      .build();
    
    S3ClientBuilder b = S3Client.builder()
      .region(s3props.getRegion())
      .credentialsProvider(credentialsProvider)
      .serviceConfiguration(s3Configuration);

    return b.build();
  }

  @Bean
  @ConditionalOnProperty(name = "aws.s3.enabled", havingValue = "true", matchIfMissing = false)
  public AwsCredentialsProvider awsCredentialsProvider(S3ClientConfigurationProperties s3props) {
    if (StringUtils.isBlank(s3props.getAccessKeyId())) {
      return DefaultCredentialsProvider.create();
    } else {
      return () -> AwsBasicCredentials.create(
        s3props.getAccessKeyId(),
        s3props.getSecretAccessKey());
    }
  }
  
  @Bean
  @ConditionalOnProperty(name = "aws.s3.enabled", havingValue = "true", matchIfMissing = false)
  public S3StoragePersistencePortImpl s3StoragePersistencePort(S3Client s3Client, S3ClientConfigurationProperties s3properties) {
    log.info("[I64] - S3 ENABLED");
    return new S3StoragePersistencePortImpl(s3Client, s3properties);
  }
}
