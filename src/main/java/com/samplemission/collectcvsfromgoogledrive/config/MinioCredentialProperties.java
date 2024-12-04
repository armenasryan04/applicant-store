package com.samplemission.collectcvsfromgoogledrive.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "minio")
@Data
public class MinioCredentialProperties {

    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucketName;
}
