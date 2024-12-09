package com.samplemission.collectcvsfromgoogledrive.service.googledrive.impl;

import com.samplemission.collectcvsfromgoogledrive.config.MinioCredentialProperties;
import com.samplemission.collectcvsfromgoogledrive.service.googledrive.MinioService;
import io.minio.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MinioServiceImpl implements MinioService {

    private final MinioClient minioClient;
    private final String bucketName;
    Logger logger = LoggerFactory.getLogger(MinioServiceImpl.class);

    public MinioServiceImpl(MinioCredentialProperties properties) {
        this.minioClient = MinioClient.builder()
                .endpoint(properties.getEndpoint())
                .credentials(properties.getAccessKey(), properties.getSecretKey())
                .build();
        this.bucketName = properties.getBucketName();
    }

    @Override
    public void ensureBucketExists() throws Exception {
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    @Override
    public Set<String> getMinioFiles() throws Exception {
        return StreamSupport.stream(
                        minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build()).spliterator(), false
                )
                .map(itemResult -> {
                    try {
                        return itemResult.get().objectName();
                    } catch (Exception e) {
                        throw new RuntimeException("Error retrieving item from MinIO", e);
                    }
                })
                .collect(Collectors.toSet());
    }

    @Override
    public void uploadFile(String fileName, InputStream inputStream) throws Exception {
        ensureBucketExists();

        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(fileName)
                .stream(inputStream, -1, PutObjectArgs.MIN_MULTIPART_SIZE)
                .build());
    }


    @Override
    public void deleteFile(String fileName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .build());
            logger.info("Файл " + fileName + " успешно удален из MinIO.");
        } catch (Exception e) {
            logger.error("Ошибка при удалении файла " + fileName + " из MinIO: " + e.getMessage());
        }
    }

    @Override
    public InputStream downloadFile(String fileName) throws Exception {
        try {
            // Получаем объект из MinIO
            GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .build();
            System.out.println(fileName);
            // Возвращаем InputStream для файла
            return minioClient.getObject(getObjectArgs);
        } catch (Exception e) {
            logger.error("Ошибка при скачивании файла '{}' из MinIO: {}", fileName, e.getMessage());
            throw new RuntimeException("Error downloading file from MinIO", e);
        }
    }

}

