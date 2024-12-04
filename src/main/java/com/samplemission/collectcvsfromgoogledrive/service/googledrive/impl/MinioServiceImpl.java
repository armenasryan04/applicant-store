package com.samplemission.collectcvsfromgoogledrive.service.googledrive.impl;

import com.samplemission.collectcvsfromgoogledrive.config.MinioCredentialProperties;
import com.samplemission.collectcvsfromgoogledrive.service.googledrive.MinioService;
import io.minio.*;
import io.minio.messages.Item;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
@Service
public class MinioServiceImpl implements MinioService {

    private final MinioClient minioClient;
    private final String bucketName;

    public MinioServiceImpl(MinioCredentialProperties properties) {
        this.minioClient = MinioClient.builder()
                .endpoint(properties.getEndpoint())
                .credentials(properties.getAccessKey(), properties.getSecretKey())
                .build();
        this.bucketName = properties.getBucketName();
    }


    public void ensureBucketExists() throws Exception {
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }


    public Set<String> getMinioFiles() throws Exception {
        Set<String> minioFiles = new HashSet<>();

        Iterable<Result<Item>> items = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
        for (Result<Item> itemResult : items) {
            Item item = itemResult.get();
            minioFiles.add(item.objectName());
        }
        return minioFiles;
    }


    public void uploadFile(String fileName, InputStream inputStream) throws Exception {
        ensureBucketExists();

        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(fileName)
                .stream(inputStream, -1, PutObjectArgs.MIN_MULTIPART_SIZE)
                .build());
    }
}

