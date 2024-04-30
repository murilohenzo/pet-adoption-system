package com.murilohenzo.petapi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import software.amazon.awssdk.regions.Region;

@ConfigurationProperties(prefix = "aws.s3")
@Getter
@Setter
public class S3ClientConfigurationProperties {

  private Region region = Region.US_EAST_1;
  private String accessKeyId;
  private String secretAccessKey;
  private String bucket;

  // AWS S3 requires that file parts must have at least 5MB, except
  // for the last part. This may change for other S3-compatible services, so let't
  // define a configuration property for that
  private int multipartMinPartSize = 5*1024*1024;
}
