package com.samplemission.collectcvsfromgoogledrive.service.googledrive;

import com.samplemission.collectcvsfromgoogledrive.config.MinioCredentialProperties;
import io.minio.*;
import io.minio.messages.Item;

import java.io.InputStream;
import java.util.Set;

public interface MinioService {

    // Проверка и создание бакета, если его нет
    void ensureBucketExists() throws Exception;

    // Получаем список файлов из MinIO
      Set<String> getMinioFiles() throws Exception;

    // Загрузка файла в MinIO
     void uploadFile(String fileName, InputStream inputStream) throws Exception;
}
